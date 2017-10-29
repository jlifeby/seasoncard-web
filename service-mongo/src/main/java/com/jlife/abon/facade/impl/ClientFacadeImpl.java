package com.jlife.abon.facade.impl;

import com.jlife.abon.criteria.*;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Client;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.AbonnementsClientSearchMode;
import com.jlife.abon.facade.ClientFacade;
import com.jlife.abon.mapper.DataMapper;
import com.jlife.abon.service.ClientTagService;
import kotlin.NotImplementedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class ClientFacadeImpl extends AbstractFacade implements ClientFacade {

    @Autowired
    private ClientTagService clientTagService;

    @Autowired
    private DataMapper dataMapper;

    @Override
    public List<String> findAllClientTags(String companyId) {
        return clientTagService.findAllClientTags(companyId);
    }

    @Override
    public Page<ClientData> findClientsByTag(String tag, String companyId, Pageable pageRequest) {
        Page<Client> page = clientTagService.findClientsByTag(tag, companyId, pageRequest);
        return page.map(c -> dataMapper.toClientData(c));
    }

    @Override
    public Page<ClientData> findClients(List<SearchCriteria> criterias, String companyId, Pageable pageable) {
        List<Predicate<Client>> predicates = new ArrayList<>(criterias.size());
        for (SearchCriteria criteria : criterias) {
            if (criteria instanceof ActiveClientCriteria) {
                predicates.add(getClientPredicate((ActiveClientCriteria) criteria, companyId));
            } else if (criteria instanceof AbonnementsClientCriteria) {
                predicates.add(getClientPredicate((AbonnementsClientCriteria) criteria, companyId));
            } else if (criteria instanceof TagCriteria) {
                predicates.add(getClientPredicate((TagCriteria) criteria));
            } else if (criteria instanceof FullTextCriteria) {
                predicates.add(getClientPredicate((FullTextCriteria) criteria));
            } else if (criteria instanceof PotentialClientCriteria) {
                predicates.add(getClientPredicate((PotentialClientCriteria) criteria));
            }
        }
        final List<Client> allClients = clientService.getClients(companyId)
                .filter(predicates.stream().reduce(c -> true, Predicate::and))
                .collect(Collectors.toList());
        final List<ClientData> searchResults = allClients.stream()
                .skip((pageable.getOffset()))
                .limit(pageable.getPageSize())
                .map(c -> dataMapper.toClientData(c))
                .collect(Collectors.toList());
        populateUsersToClients(searchResults);
        return new PageImpl<>(searchResults, pageable, allClients.size());
    }

    @Override
    public Page<ClientData> findAllClients(Pageable pageable) {
        return clientService.getAllClients(pageable).map(c -> dataMapper.toClientData(c));
    }

    private Predicate<Client> getClientPredicate(AbonnementsClientCriteria criteria, String companyId) {
        return client -> {
            List<Abonnement> allAbonnementsForCompanyAndCardUUID = abonnementService.getAllAbonnementsForCompanyAndCardUUID(companyId, client.getCardUUID());
            Stream<Abonnement> stream = allAbonnementsForCompanyAndCardUUID
                    .stream();
            return getAbonnementsBasedOnMode(stream, criteria);
        };
    }

    private Predicate<Abonnement> getExpiringAbonnementPredicate(ExpiringAbonementCriteria criteria) {
        return a -> {
            final boolean fewDaysLeft = a.countDaysLeft() <= criteria.getDaysLeft();
            final AbonnementType abonType = a.getType();
            final boolean oneAttendanceLeft = abonType.equals(AbonnementType.BY_ATTENDANCE) && a.getAvailableCountOfAttendances() <= criteria.getAttendancesLeft();
            final boolean fewUnitsLeft = abonType.equals(AbonnementType.BY_UNIT) && (float) a.getAvailableCountOfUnits() / a.getInitialCountOfUnits() <= criteria.getUnitsLeftRatio();
            return fewDaysLeft || oneAttendanceLeft || fewUnitsLeft;
        };
    }

    private boolean getAbonnementsBasedOnMode(Stream<Abonnement> stream, AbonnementsClientCriteria criteria) {
        AbonnementsClientSearchMode mode = criteria.getSearchMode();
        switch (mode) {
            case ALL:
                if (criteria.getProductId() == null) {
                    return true;
                } else {
                    return stream.anyMatch(a -> criteria.getProductId().equals(a.getProductId()));
                }
            case HAS_NONE:
                return stream.count() == 0;
            case HAS_ANY:
                stream = filterWithProductId(stream, criteria);
                if (criteria.getExpiringCriteria() == null) {
                    return stream.count() > 0;
                } else {
                    return stream.filter(Abonnement::checkIsActual).anyMatch(getExpiringAbonnementPredicate(criteria.getExpiringCriteria()));
                }
            case HAS_ACTIVE:
                stream = filterWithProductId(stream, criteria);
                if (criteria.getExpiringCriteria() == null) {
                    return stream.anyMatch(Abonnement::checkIsActual);
                } else {
                    return stream.filter(Abonnement::checkIsActual).anyMatch(getExpiringAbonnementPredicate(criteria.getExpiringCriteria()));
                }
            case HAS_ONLY_NOT_ACTIVE:
                stream = filterWithProductId(stream, criteria);
                List<Abonnement> abonnements = stream.collect(Collectors.toList());
                return !abonnements.isEmpty() &&
                        abonnements.stream().noneMatch(Abonnement::checkIsActual);
            case HAS_ONLY_ACTIVE:
                throw new NotImplementedError();
            default:
                throw new NotImplementedError();
        }
    }

    private Stream<Abonnement> filterWithProductId(Stream<Abonnement> stream, AbonnementsClientCriteria criteria) {
        String productId = criteria.getProductId();
        if (productId != null) {
            stream = stream.filter(a -> productId.equals(a.getProductId()));
        }
        return stream;
    }

    private Predicate<Client> getClientPredicate(ActiveClientCriteria criteria, String companyId) {
        return client -> {
            switch (criteria.getSearchMode()) {
                case ONLY_ACTIVE:
                    return client.isActive();
                case ONLY_NOT_ACTIVE:
                    return !client.isActive();
                case BOTH:
                    return true;
                default:
                    throw new NotImplementedError();
            }
        };
    }

    private Predicate<Client> getClientPredicate(ExpiringAbonementCriteria criteria, String companyId) {
        return client -> criteria == null || abonnementService.getActiveAbonnementsForCompanyAndCardUUID(companyId, client.getCardUUID())
                .stream()
                .filter(Abonnement::checkIsActual)
                .anyMatch(getExpiringAbonnementPredicate(criteria));
    }

    private Predicate<Client> getClientPredicate(TagCriteria tagCriteria) {
        return client -> {
            if (tagCriteria == null) {
                return true;
            } else {
                final Stream<String> tagNamesStream = tagCriteria.getTags().stream()
                        .map(String::toLowerCase);
                final Stream<String> clientTagsStream = client.getTags().stream()
                        .map(String::toLowerCase);
                final Set<String> tagNames = tagNamesStream.collect(Collectors.toSet());
                final Set<String> clientTags = clientTagsStream.collect(Collectors.toSet());
                switch (tagCriteria.getSearchMode()) {
                    case IGNORE:
                        return true;
                    case EXACT:
                        return clientTags.containsAll(tagNames) && tagNames.containsAll(clientTags);
                    case ANY:
                        for (String tag : tagNames) {
                            if (clientTags.contains(tag)) {
                                return true;
                            }
                        }
                        return false;
                    case ALL:
                        return clientTags.containsAll(tagNames);
                    default:
                        return false;
                }
            }
        };
    }

    private Predicate<Client> getClientPredicate(FullTextCriteria criteria) {
        final String text = criteria.getText();
        return client -> {
            if (text == null) {
                return true;
            } else {
                final String lastName = client.getLastName();
                final String firstName = client.getName();
                final String middleName = client.getMiddleName();
                final String phone = client.getPhone();
                final String cardUUID = client.getCardUUID().toString();
                final String internalId = client.getInternalId();
                final String combinedData = String
                        .join(",", lastName, firstName, middleName, phone, cardUUID, internalId)
                        .replace(",null", "").replace("null,", "")
                        .toLowerCase();
                return combinedData.contains(text.toLowerCase());
            }
        };
    }

    private Predicate<Client> getClientPredicate(PotentialClientCriteria criteria) {
        return client -> {
            switch (criteria.getSearchMode()) {
                case REAL:
                    return !client.isPotential();
                case POTENTIAL:
                    return client.isPotential();
                case BOTH:
                    return true;
                default:
                    throw new NotImplementedError();
            }
        };
    }
}
