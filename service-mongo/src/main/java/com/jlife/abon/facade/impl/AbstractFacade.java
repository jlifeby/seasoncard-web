package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.ClientData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.entity.*;
import com.jlife.abon.facade.AdminFacade;
import com.jlife.abon.mapper.DataMapper;
import com.jlife.abon.service.*;
import com.jlife.abon.service.sms.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class AbstractFacade {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected final String DEFAULT_SMS_SERVICE = "unionSmsService";

    @Autowired
    protected AbonnementService abonnementService;

    @Autowired
    protected ClientService clientService;

    @Autowired
    protected CardService cardService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected EmailService emailService;

    @Autowired
    @Qualifier(DEFAULT_SMS_SERVICE)
    protected SmsService smsService;

    @Autowired
    protected AdminService adminService;

    @Autowired
    protected PromotionService promotionService;

    @Autowired
    protected TrainerService trainerService;

    @Autowired
    protected EnrollService enrollService;

    @Autowired
    protected MarkingAttendedService markingAttendedService;

    @Autowired
    protected CompanyStatisticService companyStatisticService;

    @Autowired
    protected DataMapper dataMapper;
    @Autowired
    protected WidgetSettingService widgetSettingService;
    @Autowired
    private AdminFacade adminFacade;

    protected void populateProductWithCompany(ConsolidatedAbonnement consolidatedAbonnement) {
        Product product = productService.getProduct(consolidatedAbonnement.getProductId());
        populateCompany(product);
        consolidatedAbonnement.setProduct(product);
    }

    protected void populateCompany(Product product) {
        Company company = companyService.getCompany(product.getCompanyId());
        product.setCompany(company);
    }

    protected void populateProduct(Abonnement abonnement) {
        Product product = productService.getProduct(abonnement.getProductId());
        abonnement.setProduct(product);
    }

    protected void populateAllAbonnementsByCompany(Card card, String companyId) {
        String cardId = card.getId();
        List<Abonnement> allAbonnementsForCompanyAndUser = abonnementService.getAllAbonnementsForCompanyAndCardId(companyId, cardId);
        for (Abonnement abonnement : allAbonnementsForCompanyAndUser) {
            if (abonnement.getProduct() == null) {
                populateProduct(abonnement);
            }
        }
        card.setAbonnements(allAbonnementsForCompanyAndUser);
    }

    protected void populateLastAbonnementsByCompany(Card card, String companyId) {
        List<Abonnement> lastAbonnements = abonnementService.getLastAbonnementsForEachProductOfCompany(card.getUserId(), companyId);
        card.setLastAbonnements(lastAbonnements);
    }

    protected void populateProducts(List<Abonnement> abonnements) {
        for (Abonnement abonnement : abonnements) {
            if (abonnement.getProduct() == null) {
                populateProduct(abonnement);
            }
            populateCompany(abonnement.getProduct());
        }
    }

    protected void populateCompanies(List<Product> products) {
        for (Product product : products) {
            populateCompany(product);
        }
    }


    protected void populateClient(Card card, String companyId) {
        String userId = card.getUserId();
        Client client = clientService.getClientByUserIdAndCompanyId(userId, companyId);
        card.setClient(client);
    }

    protected void populateClients(List<Card> cards, String companyId) {
        for (Card card : cards) {
            populateClient(card, companyId);
        }
    }

    protected void populateClientAsUser(Card card, String companyId) {
        String userId = card.getUserId();
        Client client = clientService.getClientByUserIdAndCompanyId(userId, companyId);
        // set id from user because we populate user. It will be removed after 0.7 version
        // for now required for back capability with 0.6 version on android
        client.setId(client.getUserId());
        User user = dataMapper.toUser(client);
        card.setUser(user);
    }

    protected void populateClientsAsUsers(List<Card> cards, String companyId) {
        for (Card card : cards) {
            populateClientAsUser(card, companyId);
        }
    }

    protected void populateUsers(List<Card> cards) {
        for (Card card : cards) {
            User user = userService.getUser(card.getUserId());
            card.setUser(user);
        }
    }

    protected void populateUsersToClients(List<ClientData> clientDatas) {
        for (ClientData clientData : clientDatas) {
            populateUserToClient(clientData);
        }
    }
    protected void populateUserToClient(ClientData clientData){
        User user = userService.getUser(clientData.getUserId());
        clientData.setUser(dataMapper.toUserData(user));
    }

    protected void populateUser(Card card) {
        String userId = card.getUserId();
        User user = userService.getUser(userId);
        card.setUser(user);
    }

    protected void populateCompany(Product... products) {
        for (Product product : products) {
            Company company = companyService.getCompany(product.getCompanyId());
            product.setCompany(company);
        }
    }

    protected void populateCompany(List<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            Company company = companyService.getCompany(promotion.getCompanyId());
            promotion.setCompany(company);
        }
    }

    protected void populateTrainer(Abonnement... abonnements) {
        for (Abonnement abonnement : abonnements) {
            if (abonnement.getTrainer() == null && abonnement.getTrainerId() != null) {
                Trainer trainer = trainerService.getTrainer(abonnement.getTrainerId());
                abonnement.setTrainer(trainer);
            }
            populateTrainer(abonnement.getAttendances());
        }
    }

    private void populateTrainer(List<Attendance> attendances) {
        for (Attendance attendance : attendances) {
            if (attendance.getTrainerId() != null) {
                Trainer trainer = trainerService.getTrainer(attendance.getTrainerId());
                attendance.setTrainer(trainer);
            }
        }
    }

    protected void populateAministrators(List<CompanyData> companyDatas) {
        for (CompanyData companyData : companyDatas) {
            List<UserData> companyAdmins = adminFacade.getCompanyAdmins(companyData.getId());
            companyData.setAdministrators(companyAdmins);
        }
    }
}
