package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.*;
import com.jlife.abon.entity.*;
import com.jlife.abon.enumeration.Role;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.CompanyFacade;
import com.jlife.abon.interfaces.Existence;
import com.jlife.abon.repository.*;
import com.jlife.abon.service.TariffService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class CompanyFacadeImpl extends AbstractFacade implements CompanyFacade {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TariffService tariffService;

    @Override
    public CompanyData getCompanyWithActiveProducts(String companyId) {
        Company company = companyRepository.findOne(companyId);
        List<Product> products = productRepository.findByCompanyIdAndActive(companyId, true);
        company.setProducts(products);
        return dataMapper.toCompanyData(company);
    }

    public CardData bindCard(CardData card, String companyId) {
        Long cardUUID = card.getCardUUID();
        Card existedCard = getCardForRegisteringAsCompany(cardUUID, companyId);
        AbstractUserData rowUser = card.getUser();
        User newUser = prepareUserForRegister(rowUser, cardUUID, companyId);
        String initialPassword = addInitialPasswordForUser(newUser);
        User createdUser = userRepository.save(newUser);
        existedCard.setUser(createdUser);
        existedCard.setUserId(createdUser.getId());
        existedCard.setInitializingCompany(companyId);
        existedCard.setFree(false);
        Client client = clientService.createClient(createdUser, companyId);
        existedCard.setClient(client);
        Card updateCard = cardRepository.save(existedCard);
        smsService.sendInitialPasswordBySms(newUser.getPhone(), createdUser.getId(), updateCard.getCardUUID(), initialPassword);
        return dataMapper.toCardData(updateCard);
    }

    private String addInitialPasswordForUser(User newUser) {
        String password = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        newUser.setPassword(hashedPassword);
        return password;
    }

    private User prepareUserForRegister(AbstractUserData user, Long cardUUID, String companyId) {
        User newUser = new User();
        if (cardUUID == null || user == null) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE);
        }
        if (user.getLogoPath() == null) {
            newUser.setLogoPath(DEFAULT_USER_LOGO_PATH);
        }
        if (user.getPhone() == null || StringUtils.isEmpty(user.getPhone())) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE);
        }
        if (user.getName() == null || StringUtils.isEmpty(user.getName())) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE);
        }
        if (user.getLastName() == null || StringUtils.isEmpty(user.getLastName())) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE);
        }
        if (user.getBirthday() == null) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE);
        }
        String phone = user.getPhone();
        phone = phone.replaceAll("\\+", "");
        phone = phone.replaceAll("\\s+", "");
        phone = phone.replaceAll("-", "");
        if (phone.startsWith("8029") || phone.startsWith("8033") || phone.startsWith("8044") || phone.startsWith("8025")) {
            phone = "375" + phone.substring(2);
        }
        newUser.setLogoPath(user.getLogoPath());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthday(user.getBirthday());
        newUser.setPhone(phone);
        newUser.setCountry(user.getCountry());

        newUser.setCreatedByCompany(companyId);
        newUser.setStatus(Existence.ACTIVE_STATUS);
        newUser.getRoles().add(Role.ROLE_USER.name());
        newUser.setCardUUID(cardUUID);
        return newUser;
    }

    private Card getCardForRegisteringAsCompany(Long cardUUID, String companyId) {
        Assert.notNull(cardUUID);
        Card existedCard = cardRepository.findOneByCardUUID(cardUUID);
        if (existedCard == null) {
            throw new NotAllowedException(ApiErrorCode.CARD_IS_NOT_SUPPORTED_BY_SYSTEM_WITH_CARD_UUID,
                    String.valueOf(cardUUID));
        }
        if (!existedCard.isFree() || existedCard.getUserId() != null) {
            throw new NotAllowedException(ApiErrorCode.CARD_IS_ALREADY_BUSY_WITH_CARD_UUID,
                    String.valueOf(cardUUID));
        }
        if (!companyId.equals(existedCard.getDeliveredToCompany())) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_CARD,
                    String.valueOf(cardUUID));
        }
        return existedCard;
    }

    @Override
    public ClientData createClient(ClientData clientData, String companyId) {
        Long cardUUID = clientData.getCardUUID();
        String internalId = clientData.getInternalId();
        if (internalId != null && !internalId.equals("")) {
            Client existedClientWithInternalId = clientService.getClientByInternalId(clientData.getInternalId(), companyId);
            if (existedClientWithInternalId != null) {
                throw new NotAllowedException(ApiErrorCode.INTERNAL_ID_IS_ALREADY_BUSY, internalId);
            }
        }

        Card existedCard = getCardForRegisteringAsCompany(cardUUID, companyId);
        User newUser = prepareUserForRegister(clientData, cardUUID, companyId);
        String initialPassword = addInitialPasswordForUser(newUser);
        User createdUser = userRepository.save(newUser);
        existedCard.setUser(createdUser);
        existedCard.setUserId(createdUser.getId());
        existedCard.setInitializingCompany(companyId);
        existedCard.setFree(false);
        Client client = clientService.prepareClientBasedOnUser(createdUser, companyId);
        client.setTags(clientData.getTags());
        if(internalId != null && !internalId.equals("")){
            client.setInternalId(internalId);
        }
        client.setComment(clientData.getComment());
        Client createdClient = clientService.createClient(client);
        existedCard.setClient(client);
        Card updateCard = cardRepository.save(existedCard);
        smsService.sendInitialPasswordBySms(newUser.getPhone(), createdUser.getId(), updateCard.getCardUUID(), initialPassword);
        return dataMapper.toClientData(createdClient);
    }

    @Override
    public ClientData archiveClient(Long cardUUID, String companyId) {
        Client client = clientService.archiveClient(cardUUID, companyId);
        return dataMapper.toClientData(client);
    }

    @Override
    public ClientData restoreClient(Long cardUUID, String companyId) {
        Client client = clientService.restoreClient(cardUUID, companyId);
        return dataMapper.toClientData(client);
    }

    @Override
    public Page<ClientData> findClients(String companyId, Pageable pageable) {
        Page<Client> clientDatas = clientService.getClients(companyId, pageable);
        return clientDatas.map(client -> {
            ClientData cd = dataMapper.toClientData(client);
            populateUserToClient(cd);
            return cd;
        });
    }


    @Override
    public CompanyData updateCompanyOwn(String companyId, CompanyData company) {
        Company existedCompany = companyRepository.findOne(companyId);
        if (existedCompany == null) {
            throw new ResourceNotFoundException(ApiErrorCode.COMPANY_NOT_FOUND,
                    company.getId());
        }
        existedCompany.setName(company.getName());
        existedCompany.setDescription(company.getDescription());
        existedCompany.setLogoPath(company.getLogoPath());
        existedCompany.setLogoMediaId(company.getLogoMediaId());
        existedCompany.setEmail(company.getEmail());
        existedCompany.setSite(company.getSite());
        existedCompany.setAddress(dataMapper.fromAddressData(company.getAddress()));
        existedCompany.setPhones(company.getPhones());
        Company savedCompany = companyRepository.save(existedCompany);
        return dataMapper.toCompanyData(savedCompany);
    }

    @Override
    public CompanyData updateCompanyRequisitesOwn(String companyId, CompanyRequisitesData companyRequisites) {
        // todo in future use own service to deny edit all fields
        Company savedCompany = adminService.updateCompanyRequisites(companyId, dataMapper.fromCompanyRequisitesData(companyRequisites));
        return dataMapper.toCompanyData(savedCompany);
    }

    @Override
    public CompanyData updateCompanySettings(String companyId, CompanySettingsData companySettings) {
        Company savedCompany = companyService.updateCompanySettings(companyId, dataMapper.fromCompanySettingsData(companySettings));
        return dataMapper.toCompanyData(savedCompany);
    }

    @Override
    public ClientData updateClientByUserId(String userId, ClientData clientData, String companyId) {
        Client mappedClient = dataMapper.fromClientData(clientData);
        if (clientData.getTags() == null) {
            mappedClient.setTags(null);
        }
        Client client = clientService.updateClientByUserId(userId,
                mappedClient, companyId);
        return dataMapper.toClientData(client);
    }

    @Override
    public ClientData updateClientByCardUUID(Long cardUUID, ClientData clientData, String companyId) {
        Client mappedClient = dataMapper.fromClientData(clientData);
        if (clientData.getTags() == null) {
            mappedClient.setTags(null);
        }
        Client client = clientService.updateClientByCardUUID(cardUUID,
                mappedClient, companyId);

        return dataMapper.toClientData(client);
    }

    @Override
    public List<CompanyData> getAllCompaniesWithProducts() {
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            List<Product> products = productRepository.findByCompanyId(company.getId());
            company.setProducts(products);
        }
        return dataMapper.toCompanyDataList(companies);
    }

    @Override
    public List<CompanyData> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return dataMapper.toCompanyDataList(companies);
    }

    @Override
    public List<CompanyData> getAllPublishedCompanies() {
        List<Company> companies = companyService.getAllPublishedCompanies();
        return dataMapper.toCompanyDataList(companies);
    }

    @Override
    public CompanyData getCompany(String companyId) {
        Company company = companyRepository.findOne(companyId);
        return dataMapper.toCompanyData(company);
    }

    @Override
    public CompanyData getCompanyWithActivePublishedProducts(String companyId) {
        Company company = companyService.getCompany(companyId);
        List<Product> activePublishedProducts = productService.getActivePublishedProductsForCompany(companyId);
        company.setProducts(activePublishedProducts);
        return dataMapper.toCompanyData(company);
    }

    @Override
    public CompanyData updateCompanyProductLogoPathSettings(String productLogoPath, String companyId) {
        Company company = companyService.updateDefaultProductLogo(productLogoPath, companyId);
        return dataMapper.toCompanyData(company);
    }

    @Override
    public void acceptAgreementAndPublicOffer(String companyId) {
        companyService.acceptAgreementAndPublicOffer(companyId);
    }

    @Override
    public boolean checkCompanyIsAcceptedAgreement(String companyId) {
        return companyService.checkCompanyIsAcceptedAgreement(companyId);
    }

    @Override
    public boolean checkCompanyIsAcceptedOffer(String companyId) {
        return companyService.checkCompanyIsAcceptedOffer(companyId);
    }

    @Override
    public CompanyData saveRaw(CompanyData rowCompany) {
        Company savedCompany = companyRepository.save(dataMapper.fromCompanyData(rowCompany));
        return dataMapper.toCompanyData(savedCompany);
    }

    @Override
    public ClientData save(ClientData clientData) {
        Client client = clientRepository.save(dataMapper.fromClientData(clientData));
        return dataMapper.toClientData(client);
    }

    @Override
    public CompanyData setCurrentTariffAsCompany(int countOfMonth, String tariffId, String companyId) {
        Tariff calculatedTariff = tariffService.calculateTariff(countOfMonth, tariffId, companyId);
        Company company = tariffService.setCurrentTariff(calculatedTariff, companyId);
        return dataMapper.toCompanyData(company);
    }

    @Override
    public List<CompanyData> getAllCompaniesWithAdmins() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyData> companyDatas = dataMapper.toCompanyDataList(companies);
        populateAministrators(companyDatas);
        return companyDatas;
    }


}
