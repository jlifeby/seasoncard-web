package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.CardData;
import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CardFacade {
    @Deprecated
    CardData getCardWithUser(Long cardUUID);

    CardData getCardWithClient(Long cardUUID, String companyId);

    CardData getCardWithActiveAbonnementsForCompany(String companyId, Long cardUUID);

    List<CardData> getClientCards(String companyId);

    List<CardData> getCardsWithPopulatedUsers(Iterable<String> cardIds, String companyId);

    // todo move business checking to service
    @Deprecated()
    AbonnementData enrollProduct(Long cardUUID, String productId, String companyId);

    CardData getCardForUserFullPopulated(String userId);

    List<CardData> getAllCards();

    List<CardData> getDeliveredToCompanyCards(String companyId);

    AbonnementData enrollProduct(Long cardUUID, String productId, String companyId, DateTime startDate, DateTime endDate,
                                 String comment);

    CardData getClientCard(String companyId, Long cardUUID);

    @Deprecated
    AbonnementData enrollProduct(Long cardUUID,
                                 String productId,
                                 String companyId,
                                 DateTime startDate,
                                 DateTime endDate,
                                 Integer attendances,
                                 Integer units,
                                 Double price,
                                 String message);

    @Deprecated
    AbonnementData enrollProduct(Long cardUUID,
                                 String productId,
                                 String companyId,
                                 DateTime startDate,
                                 DateTime endDate,
                                 Integer attendances,
                                 Integer units,
                                 Double price,
                                 String trainerId,
                                 String message);

    @Deprecated
    AbonnementData enrollProduct(Long cardUUID,
                                 String productId,
                                 String companyId,
                                 DateTime startDate,
                                 DateTime endDate,
                                 Integer attendances,
                                 Integer units,
                                 String promotionId,
                                 String message);

    @Deprecated
    AbonnementData enrollProduct(Long cardUUID,
                                 String productId,
                                 String companyId,
                                 DateTime startDate,
                                 DateTime endDate,
                                 Integer attendances,
                                 Integer units,
                                 String promotionId,
                                 String trainerId,
                                 String message);

    List<CardData> findClientsByNamesAndCard(String text, String companyId, Pageable pageable);

    List<CardData> findClientsByNamesAndCardPartial(String text, String companyId, Pageable pageable);

    CardData getNextFreeVirtualCard(String companyId);

    CardData replaceWithNewCardAsCompany(String userId, long newCardUUID, String companyId);

    CardData findByNfcUUID(String nfcUUID);

    CardData findByUserId(String userId);

    CardData findOneByNfcUUID(String cardNfcUUID);

    CardData save(CardData rowCard);

    CardData findOneByCardUUID(Long cardUUID);
}
