package com.jlife.abon.facade.impl;

import com.google.common.collect.Lists;
import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.Product;
import com.jlife.abon.entity.User;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.EnrollFacade;
import com.jlife.abon.repository.CardRepository;
import com.jlife.abon.repository.ProductRepository;
import com.jlife.abon.repository.UserRepository;
import com.jlife.abon.service.EnrollService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class CardFacadeImpl extends AbstractFacade implements CardFacade {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EnrollService enrollService;

    @Override
    @Deprecated
    public CardData getCardWithUser(Long cardUUID) {
        Card card = cardRepository.findOneByCardUUID(cardUUID);
        // todo move to service
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        if (card.isFree() || card.getUserId() == null) {
            throw new NotAllowedException(ApiErrorCode.CARD_NOT_FOUND_OR_IS_NOT_INITIALIZED_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        User user = userRepository.findOneByCardUUID(cardUUID);
        card.setUser(user);
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData getCardWithClient(Long cardUUID, String companyId) {
        Card card = cardService.getClientCardByUUID(cardUUID, companyId);
        populateClient(card, companyId);
        populateClientAsUser(card, companyId);
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData getCardWithActiveAbonnementsForCompany(String companyId, Long cardUUID) {
        Card card = cardService.getClientCardByUUID(cardUUID, companyId);
        String cardId = card.getId();
        List<Abonnement> activeAbonnementsForCompanyAndUser = abonnementService.getActiveAbonnementsForCompanyAndCard(companyId, cardId);
        for (Abonnement abonnement : activeAbonnementsForCompanyAndUser) {
            String productId = abonnement.getProductId();
            Product product = productRepository.findOne(productId);
            if (abonnement.getProduct() == null) {
                abonnement.setProduct(product);
            }
        }
        card.setAbonnements(activeAbonnementsForCompanyAndUser);
        populateClientAsUser(card, companyId);
        populateClient(card, companyId);
        return dataMapper.toCardData(card);
    }

    @Override
    public List<CardData> getClientCards(String companyId) {
        Stream<Client> companyClients = clientService.getClients(companyId);
        List<Card> cards = companyClients
                .map(c -> cardService.getClientCardByUUID(c.getCardUUID(), companyId))
                .collect(Collectors.toList());
        populateClientsAsUsers(cards, companyId);
        populateClients(cards, companyId);
        return dataMapper.toCardDataList(cards);
    }

    @Override
    public List<CardData> getCardsWithPopulatedUsers(Iterable<String> cardIds, String companyId) {
        List<Card> cards = Lists.newArrayList(cardRepository.findAll(cardIds));
        populateClientsAsUsers(cards, companyId);
        populateClients(cards, companyId);
        return dataMapper.toCardDataList(cards);
    }


    // todo move business checking to service
    @Override
    @Deprecated()
    public AbonnementData enrollProduct(Long cardUUID, String productId, String companyId) {
        AbonnementData abonnement = enrollProduct(cardUUID, productId, companyId, new DateTime(), new DateTime().plusMonths(1), "");
        productService.increaseSelling(productId);
        return abonnement;
    }


    @Override
    public CardData getCardForUserFullPopulated(String userId) {
        Card card = cardRepository.findOneByUserId(userId);
        List<Abonnement> allAbonnements = abonnementService.getAllAbonnementsOneCard(card.getId());
        List<Abonnement> lastAbonnements = abonnementService.getLastAbonnementForEachProduct(userId);
        populateProducts(allAbonnements);
        populateProducts(lastAbonnements);
        populateUser(card);
        card.setAbonnements(allAbonnements);
        card.setLastAbonnements(lastAbonnements);
        return dataMapper.toCardData(card);
    }


    @Override
    public List<CardData> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return dataMapper.toCardDataList(cards);
    }

    @Override
    public List<CardData> getDeliveredToCompanyCards(String companyId) {
        List<Card> cards = cardRepository.findByDeliveredToCompany(companyId);
        return dataMapper.toCardDataList(cards);
    }

    @Override
    public AbonnementData enrollProduct(Long cardUUID, String productId, String companyId, DateTime startDate, DateTime endDate,
                                        String comment) {
        Abonnement abonnement = enrollService.enrollProduct(cardUUID, productId, companyId, startDate, endDate, comment);
        productService.increaseSelling(productId);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public CardData getClientCard(String companyId, Long cardUUID) {
        Card card = cardService.getClientCardByUUID(cardUUID, companyId);
        populateAllAbonnementsByCompany(card, companyId);
        populateLastAbonnementsByCompany(card, companyId);
        populateClientAsUser(card, companyId);
        populateClient(card, companyId);
        populateTrainer(card.getLastAbonnements().toArray(new Abonnement[]{}));
        populateTrainer(card.getAbonnements().toArray(new Abonnement[]{}));
        return dataMapper.toCardData(card);
    }

    /**
     * @param cardUUID
     * @param productId
     * @param companyId
     * @param startDate
     * @param endDate
     * @param attendances
     * @param units
     * @param price
     * @param message
     * @return
     * @deprecated please user {@link EnrollFacade#enrollAbonnement(Long, String, AbonnementData)} instead
     */
    @Override
    @Deprecated
    public AbonnementData enrollProduct(Long cardUUID,
                                        String productId,
                                        String companyId,
                                        DateTime startDate,
                                        DateTime endDate,
                                        Integer attendances,
                                        Integer units,
                                        Double price,
                                        String message) {
        Abonnement abonnement = enrollService.enrollProduct(cardUUID, productId, companyId, startDate, endDate,
                attendances, units, price, message);
        productService.increaseSelling(productId);
        return dataMapper.toAbonnementData(abonnement);
    }

    /**
     * Enroll abonnement for specific trainer without promotion.
     *
     * @deprecated please user {@link EnrollFacade#enrollAbonnement(Long, String, AbonnementData)} instead
     */
    @Override
    @Deprecated
    public AbonnementData enrollProduct(Long cardUUID,
                                        String productId,
                                        String companyId,
                                        DateTime startDate,
                                        DateTime endDate,
                                        Integer attendances,
                                        Integer units,
                                        Double price,
                                        String trainerId,
                                        String message) {
        Abonnement abonnement = new Abonnement();
        abonnement.setProductId(productId);
        abonnement.setStartDate(startDate);
        abonnement.setEndDate(endDate);
        abonnement.setInitialCountOfAttendances(attendances);
        abonnement.setInitialCountOfUnits(units);
        abonnement.setProductPrice(price);
        abonnement.setTrainerId(trainerId);
        abonnement.addComment(message);
        Abonnement purchased = enrollService.enrollAbonnement(cardUUID, companyId, abonnement);
        productService.increaseSelling(productId);
        return dataMapper.toAbonnementData(purchased);
    }

    /**
     * @param cardUUID
     * @param productId
     * @param companyId
     * @param startDate
     * @param endDate
     * @param attendances
     * @param units
     * @param promotionId
     * @param message
     * @return
     * @deprecated please user {@link EnrollFacade#enrollAbonnement(Long, String, AbonnementData)} instead
     */
    @Override
    @Deprecated
    public AbonnementData enrollProduct(Long cardUUID,
                                        String productId,
                                        String companyId,
                                        DateTime startDate,
                                        DateTime endDate,
                                        Integer attendances,
                                        Integer units,
                                        String promotionId,
                                        String message) {
        Abonnement abonnement = enrollService.enrollProductWithPromotion(cardUUID, productId, companyId, startDate,
                endDate, attendances, units, promotionId, message);
        productService.increaseSelling(productId);
        return dataMapper.toAbonnementData(abonnement);
    }


    //TODO DM add trainerId to enroll

    /**
     * @param cardUUID
     * @param productId
     * @param companyId
     * @param startDate
     * @param endDate
     * @param attendances
     * @param units
     * @param promotionId
     * @param trainerId
     * @param message
     * @return
     * @deprecated please user {@link EnrollFacade#enrollAbonnement(Long, String, AbonnementData)} instead
     */
    @Override
    @Deprecated
    public AbonnementData enrollProduct(Long cardUUID,
                                        String productId,
                                        String companyId,
                                        DateTime startDate,
                                        DateTime endDate,
                                        Integer attendances,
                                        Integer units,
                                        String promotionId,
                                        String trainerId,
                                        String message) {
        Abonnement abonnement = new Abonnement();
        abonnement.setProductId(productId);
        abonnement.setStartDate(startDate);
        abonnement.setEndDate(endDate);
        abonnement.setInitialCountOfAttendances(attendances != null ? attendances : 0);
        abonnement.setInitialCountOfUnits(units != null ? units : 0);
        abonnement.setPromotionId(promotionId);
        abonnement.setTrainerId(trainerId);
        abonnement.addComment(message);
        Abonnement purchased = enrollService.enrollAbonnement(cardUUID, companyId, abonnement);
        productService.increaseSelling(productId);
        return dataMapper.toAbonnementData(purchased);
    }

    @Override
    public List<CardData> findClientsByNamesAndCard(String text, String companyId, Pageable pageable) {
        List<Card> clientCards = cardService.findByNamesAndCardUUID(text, companyId, pageable);
        populateClients(clientCards, companyId);
        populateClientsAsUsers(clientCards, companyId);
        return dataMapper.toCardDataList(clientCards);
    }

    @Override
    public List<CardData> findClientsByNamesAndCardPartial(String text, String companyId, Pageable pageable) {
        List<Card> clientCards = clientService.findByNamesAndCardUUIDPartial(text, companyId, pageable);
        populateClients(clientCards, companyId);
        populateClientsAsUsers(clientCards, companyId);
        return dataMapper.toCardDataList(clientCards);
    }

    @Override
    public CardData getNextFreeVirtualCard(String companyId) {
        Card card = cardService.getNextFreeVirtualCard(companyId);
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData replaceWithNewCardAsCompany(String userId, long newCardUUID, String companyId) {
        Card card = cardService.replaceWithNewCardAsCompany(userId, newCardUUID, companyId);
        User user = userService.getUser(userId);
        smsService.sendReplacingCardMessage(user.getPhone(), userId, newCardUUID, companyId);
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData findByNfcUUID(String nfcUUID) {
        Card card = cardRepository.findOneByNfcUUID(nfcUUID);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_NFC_UUID,
                    nfcUUID);
        }
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData findByUserId(String userId) {
        Card card = cardRepository.findOneByUserId(userId);
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData findOneByNfcUUID(String cardNfcUUID) {
        Card card = cardRepository.findOneByNfcUUID(cardNfcUUID);
        return dataMapper.toCardData(card);
    }

    @Override
    public CardData save(CardData rowCard) {
        Card savedCard = cardRepository.save(dataMapper.fromCardData(rowCard));
        return dataMapper.toCardData(savedCard);
    }

    @Override
    public CardData findOneByCardUUID(Long cardUUID) {
        Card card = cardRepository.findOneByCardUUID(cardUUID);
        return dataMapper.toCardData(card);
    }
}
