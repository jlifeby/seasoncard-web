package com.jlife.abon.service;

import com.jlife.abon.aggregation.FindingClientResult;
import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.User;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.ClientRepository;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class ClientService {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardService cardService;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Client getClientByUserIdAndCompanyId(String userId, String companyId) {
        Client client = clientRepository.findOneByUserIdAndCompanyId(userId, companyId);
        if (client == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CLIENT_NOT_FOUND, userId);
        }
        return client;
    }

    public Client updateClientByUserId(String userId, Client client, String companyId) {
        Client existedClient = this.getClientByUserIdAndCompanyId(userId, companyId);
        String internalId = client.getInternalId();
        if (internalId != null && !internalId.equals("")) {
            Client existedClientWithInternalId = this.getClientByInternalId(internalId, companyId);
            if (existedClientWithInternalId != null && !existedClientWithInternalId.getId().equals(existedClient.getId())) {
                throw new NotAllowedException(ApiErrorCode.INTERNAL_ID_IS_ALREADY_BUSY, internalId);
            }
        }
        existedClient.setName(client.getName());
        existedClient.setLastName(client.getLastName());
        existedClient.setBirthday(client.getBirthday());
        existedClient.setLogoPath(client.getLogoPath());
        existedClient.setLogoMedia(client.getLogoMedia());
        existedClient.setLogoMediaId(client.getLogoMediaId());
        existedClient.setVehicleRegistrationPlate(client.getVehicleRegistrationPlate());
        existedClient.setCountry(client.getCountry());
        if (client.getComment() != null) {
            existedClient.setComment(client.getComment());
        }
        if (client.getTags() != null) {
            existedClient.setTags(client.getTags());
        }
        if (internalId != null) {
            if (!internalId.equals("")) {
                existedClient.setInternalId(internalId);
            } else {
                existedClient.setInternalId(null);
            }
        }
        return clientRepository.save(existedClient);
    }

    public Client updateClientByCardUUID(Long cardUUID, Client client, String companyId) {
        Card card = cardService.getClientCardByUUID(cardUUID, companyId);
        return updateClientByUserId(card.getUserId(), client, companyId);
    }

    public Client createClient(User user, String companyId) {
        Client client = prepareClientBasedOnUser(user, companyId);
        return createClient(client);
    }

    public Client createClient(User user, String companyId, String comment) {
        Client client = prepareClientBasedOnUser(user, companyId);
        client.setComment(comment);
        return createClient(client);
    }

    public Client prepareClientBasedOnUser(User user, String companyId) {
        Client client = new Client();
        client.setCompanyId(companyId);
        client.setUserId(user.getId());
        client.setCardUUID(user.getCardUUID());
        client.setName(user.getName());
        client.setLastName(user.getLastName());
        client.setBirthday(user.getBirthday());
        client.setPhone(user.getPhone());
        client.setLogoPath(user.getLogoPath());
        client.setLogoMedia(user.getLogoMedia());
        client.setLogoMediaId(user.getLogoMediaId());
        client.setVehicleRegistrationPlate(user.getVehicleRegistrationPlate());
        client.setCreatedDate(new DateTime());
        client.setCountry(user.getCountry());
        client.setPotential(user.isPotential());
        return client;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client createClientByCardUUID(String companyId, Long cardUUID) {
        User user = userService.getUserByCardUUD(cardUUID);
        return createClient(user, companyId);
    }

    public boolean hasClientWithCardUUID(String companyId, Long cardUUID) {
        Client client = clientRepository.findOneByCompanyIdAndCardUUID(companyId, cardUUID);
        return client != null;
    }

    public Stream<Client> getClients(String companyId) {
        Stream<Client> clients = clientRepository.findByCompanyId(companyId);
        return clients;
    }

    public Page<Client> getClients(String companyId, Pageable pageable) {
        return clientRepository.findByCompanyId(companyId, pageable);
    }

    public List<Client> getNewClientsForCompanyBetweenDays(String companyId, DateTime startDate, DateTime endDate) {
        return clientRepository.findByCompanyIdAndCreatedDateBetween(companyId, startDate, endDate);
    }

    public List<Client> findByNamesAndCardUUIDByFullTextSearch(String text, String companyId, Pageable pageable) {
        String[] words = text.split("\\s+");
        List<String> names = Arrays.stream(words)
                .filter(s -> !s.matches("^-?\\d+$"))
                .map(String::toLowerCase)
                .map(WordUtils::capitalize) // todo analyze in future. For now I haven't found solution to case ignore
                .collect(Collectors.toList());
        List<Long> uuids = Arrays.stream(words)
                .filter(s -> s.matches("^-?\\d+$"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Client> clientsByNames;
        if (!names.isEmpty()) {
            TextCriteria textCriteria = TextCriteria.forLanguage("ru");
            textCriteria.matchingAny(names.toArray(new String[]{}));
            clientsByNames = clientRepository.findByCompanyId(companyId, textCriteria, pageable);
        } else {
            clientsByNames = Collections.emptyList();
        }
        List<Client> clientsByUUIDs = clientRepository.findOneByCompanyIdAndCardUUIDIn(companyId, uuids);
        List<Long> foundUUIDs = clientsByUUIDs
                .stream()
                .map(Client::getCardUUID)
                .collect(Collectors.toList());
        List<Client> allSuitableClients = new ArrayList<>(clientsByUUIDs);
        for (Client clientsByName : clientsByNames) {
            if (foundUUIDs.contains(clientsByName.getCardUUID())) {
                continue;
            }
            allSuitableClients.add(clientsByName);
        }

        return allSuitableClients;
    }

    public List<Card> findByNamesAndCardUUIDPartial(String text, String companyId, Pageable pageable) {
        // todo optimize
        // https://docs.mongodb.org/manual/reference/operator/query/regex/

        /**
         { "aggregate" : "clients" , "pipeline" : [
         { "$match" : { "companyId" : "1"}} ,
         { "$project" : { "cardUUID" : 1 , "fullNameWithCard" : { "$concat" : [ "$name" , " " , "$lastName" , " " , { "$substr" : [ "$cardUUID" , 0 , 12]}]}}} ,
         { "$match" : { "fullNameWithCard" : { "$regex" : "1|123" , "$options" : "i"}}} ,
         { "$sort" : { "fullNameWithCard" : 1}} ,
         { "$skip" : 0} ,
         { "$limit" : 1000}
         ]}
         **/


        String[] words = text.split("\\s+");
        String regex = Arrays.stream(words)
                .collect(Collectors.joining("|"));

        String concatExpression = "concat(name, ' ', lastName, ' ', substr(cardUUID, 0, 12))";
        Aggregation agg = newAggregation(
                match(Criteria.where("companyId").is(companyId)),
                project("cardUUID").andExpression(concatExpression).as("fullNameWithCard"),
                match(Criteria.where("fullNameWithCard").regex(Pattern.compile(regex, Pattern.CASE_INSENSITIVE))),
                sort(Sort.Direction.ASC, "fullNameWithCard"),
                skip(pageable.getOffset()),
                limit(pageable.getPageSize())
        );

        //Convert the aggregation result into a List
        AggregationResults<FindingClientResult> groupResults
                = mongoTemplate.aggregate(agg, Client.class, FindingClientResult.class);
        List<FindingClientResult> result = groupResults.getMappedResults();

        List<Card> cards = new ArrayList<>();
        for (FindingClientResult findingClientResult : result) {
            Card card = cardService.getClientCardByUUID(findingClientResult.getCardUUID(), companyId);
            cards.add(card);
        }
        return cards;
    }

    public Client getClientWithCardUUID(Long cardUUID, String companyId) {
        Client client = clientRepository.findOneByCompanyIdAndCardUUID(companyId, cardUUID);
        if (client == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CLIENT_NOT_FOUND_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        return client;
    }

    public int countAlliClients(String companyId) {
        return clientRepository.countByCompanyId(companyId);
    }

    public Client getClientByInternalId(String internalId, String companyId) {
        return clientRepository.findOneByCompanyIdAndInternalId(companyId, internalId);
    }

    public Client archiveClient(Long cardUUID, String companyId) {
        Client client = getClientWithCardUUID(cardUUID, companyId);
        client.setActive(false);
        Client updated = clientRepository.save(client);
        return updated;
    }

    public Client restoreClient(Long cardUUID, String companyId) {
        Client client = getClientWithCardUUID(cardUUID, companyId);
        client.setActive(true);
        Client updated = clientRepository.save(client);
        return updated;
    }

    public void updateClientPhonesByCardUUID(Long cardUUID, String newPhone) {
        List<Client> clients = clientRepository.findByCardUUID(cardUUID);
        for (Client client : clients) {
            client.setPhone(newPhone);
        }
        clientRepository.save(clients);
    }

    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @NotNull
    public Client makeClientReal(long cardUUID, @NotNull String companyId) {
        Client client = getClientWithCardUUID(cardUUID, companyId);
        client.setPotential(false);
        return clientRepository.save(client);
    }

    public Stream<Client> getAllClientsForCompany(String companyId) {
        return clientRepository.findByCompanyIdOrderByLastNameAsc(companyId);
    }
}
