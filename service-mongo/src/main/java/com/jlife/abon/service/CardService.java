package com.jlife.abon.service;

import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.User;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.service.sms.SmsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class CardService extends AbstractService {

    @Autowired
    private ClientService clientService;

    @Autowired
    @Qualifier(DEFAULT_SMS_SERVICE)
    private SmsService smsService;

    @Autowired
    private UserService userService;

    public Card getCardByUserId(String userId) {
        Card card = cardRepository.findOneByUserId(userId);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_FOR_USER_ID, userId);
        }
        return card;
    }

    // todo not allow exception if not exist
    public Card getClientCardByUUID(Long cardUUID, String companyId) {
        Card card = cardRepository.findOneByCardUUIDAndFree(cardUUID, false);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_OR_IS_NOT_INITIALIZED_WITH_CARD_UUID,
                    String.valueOf(cardUUID));
        }
        if (!clientService.hasClientWithCardUUID(companyId, cardUUID)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_CLIENT_WITH_CARD, String.valueOf(cardUUID));
        }
        return card;
    }

    public Card getCard(String cardId) {
        Card card = cardRepository.findOne(cardId);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_ID, cardId);
        }
        return card;
    }

    public List<Card> findByNamesAndCardUUID(String text, String companyId, Pageable pageable) {
        List<Client> clients = clientService.findByNamesAndCardUUIDByFullTextSearch(text, companyId, pageable);
        List<Card> clientCards = new ArrayList<>();
        for (Client client : clients) {
            Card card = cardRepository.findOneByCardUUID(client.getCardUUID());
            clientCards.add(card);
        }
        return clientCards;
    }

    public Card getNextFreeVirtualCard(String companyId) {
        Card card = cardRepository.findOneByDeliveredToCompanyAndFreeAndNfcUUIDNull(companyId, true);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.COMPANY_DOES_NOT_HAVE_FREE_VIRTUAL_CARD, companyId);
        }
        return card;
    }

    public Card replaceWithNewCardAsCompany(String userId, long newCardUUID, String companyId) throws AbonRuntimeException {
        LOG.info("Start replacing card for user id = " + userId + " with new card uuid = " + newCardUUID);
        try {
            User user = userService.getUser(userId);
            if (user.getCardUUID() == null) {
                throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE, "user with id=" + userId + " doesn't have a card");
            }
            Card previousCard = getCardByUUID(user.getCardUUID());
            Long previousCardUUID = previousCard.getCardUUID();
            String previousCardId = previousCard.getId();
            Card newCard = verifyCardForRegister(newCardUUID, companyId);
            // occupy new card: part 1
            newCard.setFree(false);
            newCard.setInitializingCompany(companyId);
            newCard = cardRepository.save(newCard);

            // release previous card
            previousCard.setUserId(null);
            previousCard.setPreviousUserId(userId);
            cardRepository.save(previousCard);

            // occupy new Card: part 2
            newCard.setUserId(userId);
            newCard = cardRepository.save(newCard);
            String newCardId = newCard.getId();

            // update user with new card uuid
            user.setCardUUID(newCardUUID);
            userRepository.save(user);

            updateAllClients(userId, newCardUUID);
            moveAbonnementsToNewCard(previousCardId, newCardId);
            return newCard;
        } catch (AbonRuntimeException e) {
            LOG.error("Error when replacing card for user", e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error when replacing card for user", e);
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE, e.getMessage());
        }
    }

    @NotNull
    public Card verifyCardForRegister(long newCardUUID, String companyId) {
        Card newCard = cardRepository.findOneByCardUUIDAndFree(newCardUUID, true);
        if (newCard == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, String.valueOf(newCardUUID));
        }
        if (!companyId.equals(newCard.getDeliveredToCompany())) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_CARD, String.valueOf(newCardUUID));
        }
        return newCard;
    }

    @NotNull
    private Card getCardByUUID(Long cardUUID) {
        Card previousCard = cardRepository.findOneByCardUUID(cardUUID);
        if (previousCard == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        return previousCard;
    }

    private void moveAbonnementsToNewCard(String previousCardId, String newCardId) {
        List<Abonnement> abonnementList = abonnementRepository.findByCardId(previousCardId);
        for (Abonnement abonnement : abonnementList) {
            abonnement.setCardId(newCardId);
        }
        abonnementRepository.save(abonnementList);
    }

    private void updateAllClients(String userId, long newCardUUID) {
        List<Client> clients = clientRepository.findByUserId(userId);
        for (Client client : clients) {
            client.setCardUUID(newCardUUID);
        }
        clientRepository.save(clients);
    }

    public Card occupyFreeVirtualCardForSelfRegister(String companyId) {
        Card card = cardRepository.findOneByFreeAndNfcUUIDNullAndDeliveredToCompanyNull(true);
        if (card == null) {
            throw new ResourceNotFoundException(ApiErrorCode.NO_FREE_VIRTUAL_CARDS_TO_SELF_REGISTER_CLIENT);
        }
        card.setDeliveredToCompany(companyId);
        card.setFree(false);
        return cardRepository.save(card);
    }
}
