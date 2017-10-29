package com.jlife.abon.service;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.ConsolidatedAbonnement;
import com.jlife.abon.entity.Product;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.AbonnementRepository;
import com.jlife.abon.repository.CardRepository;
import com.jlife.abon.repository.EntityRepository;
import com.jlife.abon.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Dzmitry Misiuk
 */
public class AbonnementService extends AbstractService implements EntityAbstractService<Abonnement> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CompanyService companyService;

    public Abonnement buyAbonnement(String cardId, String productId) {
        Product product = productRepository.findOne(productId);
        Card card = cardRepository.findOne(cardId);
        if (product == null || card == null) {
            throw new RuntimeException("product and card should exist");
        }
        if (!product.isActive()) {
            throw new RuntimeException("product is not active");
        }
        Abonnement abonnement = new Abonnement();
        abonnement.setProductId(productId);
        abonnement.setCardId(cardId);
        abonnement.setAvailableCountOfAttendances(product.getCountOfAttendances());
        return abonnementRepository.save(abonnement);
    }


    public List<Abonnement> getActiveAbonnementsForCompanyAndCard(String companyId, String cardId) {
        List<Product> products = productRepository.findByCompanyId(companyId);
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        return abonnementRepository.findByCardIdAndActiveAndProductIdIn(cardId, true, productIds);
    }

    public List<Abonnement> getActiveAbonnementsForCompanyAndCardUUID(String companyId, Long cardUUID) {
        final Card card = cardRepository.findOneByCardUUID(cardUUID);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, cardUUID.toString());
        } else {
            return getActiveAbonnementsForCompanyAndCard(companyId, card.getId());
        }
    }

    public List<Abonnement> getAllAbonnementsCardUUID(Long cardUUID) {
        final Card card = cardRepository.findOneByCardUUID(cardUUID);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, cardUUID.toString());
        } else {
            return abonnementRepository.findByCardId(card.getId());
        }
    }

    public List<Abonnement> getActiveAbonnementsForUser(String userId) {
        Card card = cardRepository.findOneByUserId(userId);
        return abonnementRepository.findByCardIdAndActive(card.getId(), true);
    }

    public List<Abonnement> getAllAbonnementsOneCard(String cardId) {
        return abonnementRepository.findByCardId(cardId);
    }

    public List<Abonnement> getAllAbonnementsForCompany(String companyId) {
        List<Product> products = productRepository.findByCompanyId(companyId);
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        return abonnementRepository.findByProductIdIn(productIds);
    }

    public List<Abonnement> getAllAbonnementsForUserAndProduct(String userId, String productId) {
        Card card = cardService.getCardByUserId(userId);
        List<Abonnement> abonnementList = abonnementRepository.findByCardIdAndProductId(card.getId(), productId);
        return abonnementList;
    }

    public Abonnement getLastActiveAbonnement(String userId, String productId) {
        // todo move some logic to mongoquery
        Card card = cardService.getCardByUserId(userId);
        List<Abonnement> activeAbonnements = abonnementRepository.findByCardIdAndActiveAndProductIdIn(card.getId(), true, Arrays.asList(productId));
        if (activeAbonnements.isEmpty()) {
            return null;
        }
        Abonnement lastActive = Collections.max(activeAbonnements, new Ordering<Abonnement>() {
            @Override
            public int compare(Abonnement left, Abonnement right) {
                return left.getEndDate().compareTo(right.getEndDate());
            }
        });
        return lastActive;
    }

    public Abonnement getLastActualAbonnement(String userId, String productId) {
        // todo move some logic to mongoquery
        Card card = cardService.getCardByUserId(userId);
        List<Abonnement> activeAbonnements = abonnementRepository.findByCardIdAndActiveAndProductIdIn(card.getId(), true, Arrays.asList(productId));
        if (activeAbonnements.isEmpty()) {
            return null;
        }
        Collection<Abonnement> actualAbonnements = Collections2.filter(activeAbonnements, input -> input.checkIsActual());
        if (actualAbonnements.isEmpty()) {
            return null;
        }
        Abonnement lastActualAbonnement = Collections.max(actualAbonnements, new Ordering<Abonnement>() {
            @Override
            public int compare(Abonnement left, Abonnement right) {
                return left.getEndDate().compareTo(right.getEndDate());
            }
        });
        return lastActualAbonnement;
    }

    public Abonnement getLastArchiveAbonnement(String userId, String productId) {
        // todo move some logic to mongoquery
        Card card = cardService.getCardByUserId(userId);
        List<Abonnement> archiveAbonnements = abonnementRepository.findByCardIdAndActiveAndProductIdIn(card.getId(), false, Arrays.asList(productId));
        if (archiveAbonnements.isEmpty()) {
            return null;
        }
        Abonnement lastArchiveAbonnemnet = Collections.max(archiveAbonnements, new Ordering<Abonnement>() {
            @Override
            public int compare(Abonnement left, Abonnement right) {
                return left.getEndDate().compareTo(right.getEndDate());
            }
        });
        return lastArchiveAbonnemnet;
    }

    public ConsolidatedAbonnement getConsolidateAbonnement(String userId, String productId) {
        ConsolidatedAbonnement consolidatedAbonnement = new ConsolidatedAbonnement();
        consolidatedAbonnement.setProductId(productId);
        List<Abonnement> allAbonnements = this.getAllAbonnementsForUserAndProduct(userId, productId);
        Abonnement lastActive = this.getLastActiveAbonnement(userId, productId);
        Abonnement lastArchive = this.getLastArchiveAbonnement(userId, productId);
        Abonnement lastActual = this.getLastActualAbonnement(userId, productId);
        consolidatedAbonnement.setAllAbonnements(allAbonnements);
        consolidatedAbonnement.setLastActual(lastActual);
        consolidatedAbonnement.setLastActive(lastActive);
        consolidatedAbonnement.setLastArchive(lastArchive);
        return consolidatedAbonnement;
    }

    public List<Abonnement> getLastAbonnementForEachProduct(String userId) {
        // todo move some logic to mongoquery
        // todo wtf
        Card card = cardService.getCardByUserId(userId);
        List<Abonnement> allAbonnements = getAllAbonnementsOneCard(card.getId());
        ImmutableListMultimap<String, Abonnement> groupedByProductId = Multimaps.index(allAbonnements, input -> {
            return input.getProductId();
        });
        final Map<String, List<Abonnement>> lastGroupedByProductId = new HashMap<>();
        ImmutableMap<String, Collection<Abonnement>> groupedByProductIdMap = groupedByProductId.asMap();
        setAllActual(lastGroupedByProductId, groupedByProductIdMap);
        setAllFuture(lastGroupedByProductId, groupedByProductIdMap);
        setLastFinishedIfDoesNotHaveActualOrFuture(lastGroupedByProductId, groupedByProductIdMap);
        final List<Abonnement> toReturnList = new ArrayList<>();
        lastGroupedByProductId.forEach((s, abonnements) -> toReturnList.addAll(abonnements));
        return toReturnList;
    }

    public List<Abonnement> getLastAbonnementsForEachProductOfCompany(String userId, String companyId) {
        // todo optimize
        List<Abonnement> lastAbonnementsForAllProduct = getLastAbonnementForEachProduct(userId);
        List<Abonnement> lastAbonnements = lastAbonnementsForAllProduct
                .stream()
                .filter(a -> a.getProduct().getCompanyId().equals(companyId))
                .collect(Collectors.toList());
        return lastAbonnements;
    }

    private void setLastFinishedIfDoesNotHaveActualOrFuture(final Map<String, List<Abonnement>> lastGroupedByProductId, ImmutableMap<String, Collection<Abonnement>> groupedByProductIdMap) {
        groupedByProductIdMap.forEach((productId, abonnements) -> {
            final List<Abonnement> abonList = lastGroupedByProductId.getOrDefault(productId, new ArrayList<Abonnement>());
            if (abonList.isEmpty()) {
                Collection<Abonnement> allFinished = Collections2.filter(abonnements, new Predicate<Abonnement>() {
                    @Override
                    public boolean apply(Abonnement input) {
                        return input.checkIsFinished();
                    }
                });
                Abonnement lastFinished = Collections.max(allFinished, new Ordering<Abonnement>() {
                    @Override
                    public int compare(Abonnement left, Abonnement right) {
                        return left.getEndDate().compareTo(right.getEndDate());
                    }
                });
                abonList.add(lastFinished);
            }
            lastGroupedByProductId.put(productId, abonList);
        });
    }

    private void setAllFuture(final Map<String, List<Abonnement>> lastGroupedByProductId, ImmutableMap<String, Collection<Abonnement>> groupedByProductIdMap) {
        groupedByProductIdMap.forEach(new BiConsumer<String, Collection<Abonnement>>() {
            @Override
            public void accept(String productId, Collection<Abonnement> abonnements) {
                final List<Abonnement> abonList = lastGroupedByProductId.getOrDefault(productId, new ArrayList<Abonnement>());
                abonnements.forEach(abonnement -> {
                    if (abonnement.checkIsFuture()) {
                        abonList.add(abonnement);
                    }
                });
                lastGroupedByProductId.put(productId, abonList);
            }
        });
    }

    private void setAllActual(final Map<String, List<Abonnement>> lastGroupedByProductId, ImmutableMap<String, Collection<Abonnement>> groupedByProductIdMap) {
        groupedByProductIdMap.forEach(new BiConsumer<String, Collection<Abonnement>>() {
            @Override
            public void accept(String productId, Collection<Abonnement> abonnements) {
                final List<Abonnement> abonList = lastGroupedByProductId.getOrDefault(productId, new ArrayList<Abonnement>());
                abonnements.forEach(abonnement -> {
                    if (abonnement.checkIsActual()) {
                        abonList.add(abonnement);
                    }
                });
                lastGroupedByProductId.put(productId, abonList);
            }
        });
    }

    public List<Abonnement> getAllAbonnementsForCompanyAndCardId(String companyId, String cardId) {
        List<Product> products = productRepository.findByCompanyId(companyId);
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        return abonnementRepository.findByCardIdAndProductIdIn(cardId, productIds);
    }

    public List<Abonnement> getAllAbonnementsForCompanyAndCardUUID(String companyId, Long cardUUID) {
        final Card card = cardService.getClientCardByUUID(cardUUID, companyId);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, cardUUID.toString());
        } else {
            return getAllAbonnementsForCompanyAndCardId(companyId, card.getId());
        }
    }

    public Abonnement getAbonnement(String abonnementId) {
        Abonnement abonnement = abonnementRepository.findOne(abonnementId);
        if (abonnement == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ABONNEMENT_NOT_FOUND, abonnementId);
        }
        return abonnement;
    }

    public Abonnement changeEndDate(String abonnementId, DateTime newEndDate, String comment, String companyId) {
        Abonnement abonnement = getAbonnementAsCompany(abonnementId, companyId);
        DateTimeZone requestTimeZone = DateTimeZone.forID("Europe/Minsk");
        DateTime updatedNewEndDate = newEndDate.toDateTime(requestTimeZone)
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        abonnement.setEndDate(updatedNewEndDate);
        if (StringUtils.isNoneBlank(comment)) {
            abonnement.addComment(comment);
        }
        return abonnementRepository.save(abonnement);
    }

    public Abonnement getAbonnementAsCompany(String abonnementId, String companyId) {
        Abonnement abonnement = getAbonnement(abonnementId);
        if (!abonnement.getProduct().getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_ABONNEMENT, abonnementId);
        }
        return abonnement;
    }

    public List<Abonnement> getAbonnementsForProductsSoldBetweenDays(Collection<String> productIds,
                                                                     DateTime startDate, DateTime endDate) {
        return abonnementRepository.findByPurchaseDateBetweenAndProductIdIn(startDate, endDate,
                productIds.toArray(new String[]{}));
    }


    public Abonnement addComment(String abonnementId, String comment, String companyId) {
        Abonnement abonnement = getAbonnementAsCompany(abonnementId, companyId);
        abonnement.addComment(comment);
        return abonnementRepository.save(abonnement);
    }

    public ConsolidatedAbonnement getConsolidateAbonnementAsCompany(String userId, String productId, String companyId) {
        Product product = productService.getProduct(productId);
        if (!product.getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_PRODUCT, companyId, productId);
        }
        return getConsolidateAbonnement(userId, productId);
    }

    @Override
    public EntityRepository<Abonnement> getBaseRepository() {
        return abonnementRepository;
    }

    @Override
    public String getBaseEntityName() {
        return Abonnement.class.getSimpleName();
    }
}
