package com.jlife.abon.service;

import com.jlife.abon.builder.AbonnementBuilder;
import com.jlife.abon.entity.*;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class EnrollService extends AbstractService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private TrainerService trainerService;


    public Abonnement enrollProductWithPromotion(Long cardUUID,
                                                 String productId,
                                                 String companyId,
                                                 DateTime startDate,
                                                 DateTime endDate,
                                                 Integer countOfAttendances,
                                                 Integer countOfUnits,
                                                 String promotionId,
                                                 String message) {
        double newPrice = promotionService.calculateNewPrice(productId, promotionId, companyId);
        Abonnement abonnement = prepareAbonnementToPurchase(cardUUID, productId, companyId, startDate, endDate, countOfAttendances, countOfUnits, newPrice, message);
        //applying promotion
        Promotion promotion = promotionService.getActiveOneWithOwnerCompanyId(promotionId, companyId);
        Product product = productRepository.findOne(productId);
        abonnement.setAppliedPromotions(Arrays.asList(promotion));
        double appliedPromotionValue = product.getPrice() - newPrice;
        appliedPromotionValue = (double) Math.round(appliedPromotionValue * 100d) / 100d;
        abonnement.setAppliedPromotionValue(appliedPromotionValue);
        // save
        return abonnementRepository.save(abonnement);
    }

    // todo DM return not Allow Exception when foreign client
    private Card getClientCardWithUpsertClient(Long cardUUID, String companyId) {
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

    private Abonnement prepareAbonnementToPurchase(Long cardUUID, String productId, String companyId, DateTime startDate, DateTime endDate, Integer countOfAttendances, Integer countOfUnits, double newPrice, String message) {
        Card card = getClientCardWithUpsertClient(cardUUID, companyId);

        Product product = productService.getActiveOneWithOwnerCompanyId(productId, companyId);

        product.setPrice(newPrice);
        if (product.getAbonnementType() == AbonnementType.BY_ATTENDANCE) {
            product.setCountOfAttendances(countOfAttendances);
        }
        if (product.getAbonnementType() == AbonnementType.BY_UNIT) {
            product.setCountOfUnits(countOfUnits);
        }
        product.setSelling(0);

        String cardId = card.getId();
        Abonnement abonnement = new Abonnement();

        DateTimeZone requestTimeZone = DateTimeZone.forID("Europe/Minsk");
        DateTime startDateUTZ = startDate.toDateTime(requestTimeZone);

        DateTime updatedStartDate = startDateUTZ
                .withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(1);
        DateTime endDateUTZ = endDate.toDateTime(requestTimeZone);

        DateTime updatedEndDate = endDateUTZ
                .withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        abonnement.setStartDate(updatedStartDate);
        abonnement.setEndDate(updatedEndDate);
        abonnement.setCardId(cardId);
        abonnement.setProductId(productId);
        abonnement.setProduct(product);
        abonnement.setType(product.getAbonnementType());
        if (abonnement.getType() == AbonnementType.BY_UNIT) {
            abonnement.setUnitName(product.getUnitName());
            abonnement.setInitialCountOfUnits(product.getCountOfUnits());
            abonnement.setAvailableCountOfUnits(product.getCountOfUnits());
        }
        abonnement.setAvailableCountOfAttendances(product.getCountOfAttendances());
        abonnement.setInitialCountOfAttendances(product.getCountOfAttendances());
        abonnement.setPurchaseDate(DateTime.now());
        if (StringUtils.isNoneBlank(message)) {
            abonnement.addComment(message);
        }
        return abonnement;
    }

    public Abonnement enrollProduct(Long cardUUID,
                                    String productId,
                                    String companyId,
                                    DateTime startDate,
                                    DateTime endDate,
                                    Integer countOfAttendances,
                                    Integer countOfUnits,
                                    double price,
                                    String message) {
        Abonnement abonnement = prepareAbonnementToPurchase(cardUUID, productId, companyId, startDate, endDate, countOfAttendances, countOfUnits, price, message);
        Abonnement purchasedAbonnnement = abonnementRepository.save(abonnement);
        return purchasedAbonnnement;
    }


    public Abonnement enrollProduct(Long cardUUID,
                                    String productId,
                                    String companyId,
                                    DateTime startDate,
                                    DateTime endDate,
                                    String comment) {
        Product product = productService.getActiveOneWithOwnerCompanyId(productId, companyId);
        return this.enrollProduct(cardUUID, productId, companyId, startDate, endDate,
                product.getCountOfAttendances(), product.getCountOfUnits(), product.getPrice(), comment);
    }

    public Abonnement enrollAbonnement(Long cardUUID, String companyId, Abonnement abonnement) {
        Assert.notNull(cardUUID, "card uuid should be not null");
            Assert.notNull(companyId);
        Assert.notNull(abonnement);
        Assert.notNull(abonnement.getProductId());
        Assert.notNull(abonnement.getStartDate());
        Assert.notNull(abonnement.getEndDate());

        Product baseProduct = productService.getActiveOneWithOwnerCompanyId(abonnement.getProductId(), companyId);
        Card card = getClientCardWithUpsertClient(cardUUID, companyId);

        AbonnementBuilder builder = AbonnementBuilder.getInstance()
                .withSourceAbonnement(abonnement)
                .withProduct(baseProduct)
                .withCard(card);

        if (abonnement.getPromotionId() != null) {
            Promotion promotion = promotionService.getActiveOneWithOwnerCompanyId(abonnement.getPromotionId(), companyId);
            double calculatedPrice = promotionService.calculateNewPrice(baseProduct.getId(), abonnement.getPromotionId(), companyId);
            builder.withPromotion(promotion).withCalculatedPrice(calculatedPrice);
        }

        if (abonnement.getTrainerId() != null) {
            Trainer trainer = trainerService.getActiveOneWithOwnerCompanyId(abonnement.getTrainerId(), companyId);
            builder.withTrainer(trainer);
        }
        Abonnement abonToPurchase = builder.build();
        return abonnementRepository.save(abonToPurchase);
    }
}
