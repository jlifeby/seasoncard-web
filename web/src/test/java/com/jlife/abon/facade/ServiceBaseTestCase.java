package com.jlife.abon.facade;

import com.github.fakemongo.Fongo;
import com.jlife.abon.dto.*;
import com.jlife.abon.entity.*;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.mongodb.Mongo;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/service.xml"})
public class ServiceBaseTestCase {

    public static final String DEFAULT_PRODUCT_NAME = "product_name";
    public static final String DEFAULT_CLIENT_ID = "client_junit_0";
    public static final String DEFAULT_CLIENT_INTERNAL_ID = "client_internal_id_0";
    public static final long ANOTHER_CARD_UUID = 1999L;
    public static final String ANOTHER_USER_ID = "another_user_id";
    public static final String ANOTHER_CLIENT_ID = "another_client_id";
    public static final String DEFAULT_CLIENT_PHONE = "375290000000";
    public static final int MONTH_PRICE_FOR_PAID_TARIFF = 10;
    protected static final long DEFAULT_CARD_UUID = 999;
    protected static final String DEFAULT_USER_ID = "0-junit-user";
    protected static final String DEFAULT_USER_EMAIL = "junit-first-user@example.com";
    protected static final String DEFAULT_USER_FIRST_NAME = "Junit";
    protected static final String DEFAULT_USER_LAST_NAME = "User0";
    protected static final List<String> DEFAULT_USER_TAGS = Arrays.asList("tag1", "TAG2");
    protected static final String DEFAULT_PRODUCT_ID_BY_TIME = "0-junit-product";
    protected static final String ANOTHER_PRODUCT_ID_BY_TIME = "1-junit-product";
    protected static final String DEFAULT_PRODUCT_ID_BY_UNIT = "0-junit-product-by-unit";
    protected static final String DEFAULT_COMPANY_ID = "0-junit-company";
    protected static final String ANOTHER_COMPANY_ID = "1-junit-company";
    protected static final String DEFAULT_TRAINER_ID = "0-junit-trainer";
    protected static final String NOT_COMPANY_TRAINER_ID = "1-unit-trainer";
    protected static final String DEFAULT_PROMOTION_ID = "0-junit-promotion";
    protected static final String TARIFF_FREE_ID = "0-junit-tariff";
    protected static final String TARIFF_PAID_ID = "1-junit-tariff";
    protected static final String ANOTHER_USER_FIRST_NAME = "Junit";
    protected static final String ANOTHER_USER_LAST_NAME = "User1";
    protected static final List<String> ANOTHER_USER_TAGS = Collections.singletonList("tag_three");
    @ClassRule
    public static InMemoryMongoDb inMemoryMongoDb = newInMemoryMongoDbRule().build();
    @Autowired
    protected ApplicationContext applicationContext;
    @Resource
    protected MongoTemplate mongoTemplate;
    @Resource
    protected Mongo mongo;
    @Resource
    protected ProductFacade productFacade;
    @Resource
    protected CompanyFacade companyFacade;
    @Resource
    protected PromotionFacade promotionFacade;
    @Resource
    protected UserFacade userFacade;
    @Resource
    protected CardFacade cardFacade;
    @Resource
    protected AbonnementFacade abonnementFacade;
    @Resource
    protected TariffFacade tariffFacade;
    @Resource
    protected PublicTrainerFacade trainerFacade;
    @Resource
    Fongo fongo;

    @Test
    public void testMongoTemplateForNull() {
        assertNotNull(mongoTemplate);
        assertEquals("seasoncard_junit", mongoTemplate.getDb().getName());
    }


    @Before
    public void createEssentials() {
        cleanCollectionsInFongo();

        TrainerData trainer = new TrainerData();
        trainer.setId(DEFAULT_TRAINER_ID);
        trainer.setCompanyId(DEFAULT_COMPANY_ID);
        trainerFacade.save(trainer);

        TrainerData trainerAnother = new TrainerData();
        trainerAnother.setId(NOT_COMPANY_TRAINER_ID);
        trainerFacade.save(trainerAnother);

        CompanyData company = new CompanyData();
        company.setId(DEFAULT_COMPANY_ID);
        company.setCountry(Country.RUSSIA);
        company.setContractId(-1);
        companyFacade.saveRaw(company);

        CompanyData anotherCompany = new CompanyData();
        anotherCompany.setId(ANOTHER_COMPANY_ID);
        anotherCompany.setCountry(Country.BELARUS);
        anotherCompany.setContractId(-2);
        companyFacade.saveRaw(anotherCompany);


        PromotionData promotion = new PromotionData();
        promotion.setId(DEFAULT_PROMOTION_ID);
        promotion.setCompanyId(DEFAULT_COMPANY_ID);
        promotionFacade.save(promotion);

        ProductData product = new ProductData();
        product.setId(DEFAULT_PRODUCT_ID_BY_TIME);
        product.setName(DEFAULT_PRODUCT_NAME);
        product.setCompanyId(DEFAULT_COMPANY_ID);
        product.setPrice(43);
        product.setAbonnementType(AbonnementType.BY_TIME);
        productFacade.save(product);

        ProductData anotherProductByTime = new ProductData();
        anotherProductByTime.setId(ANOTHER_PRODUCT_ID_BY_TIME);
        anotherProductByTime.setName(DEFAULT_PRODUCT_NAME);
        anotherProductByTime.setCompanyId(ANOTHER_COMPANY_ID);
        anotherProductByTime.setPrice(43);
        anotherProductByTime.setAbonnementType(AbonnementType.BY_TIME);
        productFacade.save(anotherProductByTime);

        ProductData product_by_unit = new ProductData();
        product_by_unit.setId(DEFAULT_PRODUCT_ID_BY_UNIT);
        product_by_unit.setAbonnementType(AbonnementType.BY_UNIT);
        product_by_unit.setPrice(27d);
        product_by_unit.setCompanyId(DEFAULT_COMPANY_ID);
        productFacade.save(product_by_unit);


        createClientWithCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID, DEFAULT_CLIENT_ID, DEFAULT_USER_ID,
                DEFAULT_USER_FIRST_NAME, DEFAULT_USER_LAST_NAME, DEFAULT_USER_EMAIL, DEFAULT_USER_TAGS, false);
        createClientWithCard(DEFAULT_COMPANY_ID, ANOTHER_CARD_UUID, ANOTHER_CLIENT_ID, ANOTHER_USER_ID,
                ANOTHER_USER_FIRST_NAME, ANOTHER_USER_LAST_NAME, null, ANOTHER_USER_TAGS, true);

        TariffData free_tarif = new TariffData();
        free_tarif.setId(TARIFF_FREE_ID);
        free_tarif.setFree(true);
        free_tarif.setMonthPrice(0);
        HashMap<Country, PriceData> localizedMonthPrice = new HashMap<>();
        localizedMonthPrice.put(Country.BELARUS, new PriceData(Currency.BYN, BigDecimal.ZERO));
        localizedMonthPrice.put(Country.RUSSIA, new PriceData(Currency.RUB, BigDecimal.ZERO));
        free_tarif.setLocalizedMonthPrice(localizedMonthPrice);
        tariffFacade.createTariff(free_tarif);

        TariffData paid_tariff = new TariffData();
        paid_tariff.setId(TARIFF_PAID_ID);
        paid_tariff.setFree(false);
        paid_tariff.setMonthPrice(MONTH_PRICE_FOR_PAID_TARIFF);
        HashMap<Country, PriceData> localizedMonthPricePaid = new HashMap<>();
        localizedMonthPricePaid.put(Country.BELARUS, new PriceData(Currency.BYN, BigDecimal.TEN));
        localizedMonthPricePaid.put(Country.RUSSIA, new PriceData(Currency.RUB, BigDecimal.TEN));
        paid_tariff.setLocalizedMonthPrice(localizedMonthPricePaid);
        tariffFacade.createTariff(paid_tariff);

    }

    private void createClientWithCard(String defaultCompanyId, long defaultCardUuid, String defaultClientId,
                                      String defaultUserId, String firstName, String lastName, String email,
                                      List<String> tags, boolean isPotential) {
        UserData user = new UserData();
        user.setId(defaultUserId);
        user.setCardUUID(defaultCardUuid);
        user.setPhone(DEFAULT_CLIENT_PHONE);
        user.setEmail(email);
        userFacade.save(user);

        CardData card = new CardData();
        card.setCardUUID(defaultCardUuid);
        card.setUserId(defaultUserId);
        cardFacade.save(card);


        ClientData clientData = new ClientData();
        clientData.setUserId(defaultUserId);
        clientData.setId(defaultClientId);
        clientData.setCompanyId(defaultCompanyId);
        clientData.setCardUUID(defaultCardUuid);
        clientData.setName(firstName);
        clientData.setLastName(lastName);
        clientData.setTags(tags);
        clientData.setPhone(DEFAULT_CLIENT_PHONE);
        clientData.setPotential(isPotential);
        companyFacade.save(clientData);
    }

    protected void cleanCollectionsInFongo() {
        if ("seasoncard_junit".equals(mongoTemplate.getDb().getName())) {
            List<Class<? extends Entity>> entityClasses = Arrays.asList(
                    Product.class,
                    Company.class,
                    Card.class,
                    Promotion.class,
                    Trainer.class,
                    User.class,
                    Abonnement.class,
                    SingleAttendance.class,
                    Tariff.class,
                    Client.class,
                    Account.class,
                    SmsMessage.class,
                    Transaction.class);
            for (Class<? extends Entity> entityClass : entityClasses) {
                mongoTemplate.remove(new Query(), entityClass);
            }
        }
    }

    protected void cleanCollections(Class entityClass) {
        mongoTemplate.remove(new Query(), entityClass);
    }

}

