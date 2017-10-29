package com.jlife.abon.service;

import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.CompanyRequisites;
import com.jlife.abon.entity.User;
import com.jlife.abon.enumeration.Role;
import com.jlife.abon.enumeration.UserState;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class AdminService extends AbstractService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SequenceService sequenceService;

    public Company createCompany(Company company) {
        int contractId = sequenceService.getNextContractId();
        company.setContractId(contractId);
        return companyRepository.save(company);
    }

    public Company updateCompany(String companyId, Company company) {
        Company existedCompany = companyService.getCompany(companyId);
        existedCompany.setName(company.getName());
        existedCompany.setDescription(company.getDescription());
        existedCompany.setPhones(company.getPhones());
        existedCompany.setLogoPath(company.getLogoPath());
        existedCompany.setSite(company.getSite());
        existedCompany.setEmail(company.getEmail());
        existedCompany.setLogoMedia(company.getLogoMedia());
        existedCompany.setLogoMediaId(company.getLogoMediaId());
        existedCompany.setAddress(company.getAddress());
        existedCompany.setCategories(company.getCategories());
        existedCompany.setPublished(company.isPublished());
        existedCompany.setSocialNetworks(company.getSocialNetworks());
        existedCompany.setWorkingHours(company.getWorkingHours());
        existedCompany.setCountry(company.getCountry());
        return companyRepository.save(existedCompany);
    }

    public List<Long> addNfcCardsToCompany(String companyId, long fromCard, long toCard) {
        if (fromCard > toCard) {
            throw new RuntimeException("start of rang can't be greater than end");
        }
        fromCard = fromCard - 1;
        toCard = toCard + 1;
        List<Card> freeCards = cardRepository.findByCardUUIDBetweenAndFreeAndDeliveredToCompanyNullAndNfcUUIDNotNull(fromCard, toCard, true);
        freeCards.forEach(c -> c.setDeliveredToCompany(companyId));
        List<Long> cardUUIDs = freeCards.stream().map(Card::getCardUUID).collect(Collectors.toList());
        cardRepository.save(freeCards);
        return cardUUIDs;
    }

    public User createCompanyAdmin(String companyId, User user) {
        user.setCompanyId(companyId);
        user.setUserStates(new HashSet<>(Arrays.asList(UserState.ACCEPTED_AGREEMENT, UserState.CONFIRMED_EMAIL)));
        user.getRoles().add(Role.ROLE_COMPANY.name());
        User userWithThisEmail = userRepository.findByEmail(user.getEmail());
        if (userWithThisEmail != null) {
            throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, user.getEmail());
        }
        return userRepository.save(user);
    }

    public List<User> getCompanyAdmins(String companyId) {
        return userRepository.findByCompanyIdAndRolesIn(companyId, Arrays.asList(Role.ROLE_COMPANY.name()));
    }

    public void sendCompanyAdminCredentials(String companyId, String userId) {
        User user = this.getCompanyAdmin(companyId, userId);
        if (user.getPassword() != null) {
            LOG.warn("User already has password. It will be replaced. UserId %s", userId);
        }
        Company company = companyService.getCompany(companyId);
        String password = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        emailService.sendCompanyAdminCredentials(user, company, password);
    }

    public List<Long> addVirtualCardsToCompany(String companyId, int count) {
        if(count <= 0){
            return new ArrayList<>();
        }
        Pageable pageable = new PageRequest(0, count, new Sort(Sort.Direction.ASC, "cardUUID"));
        List<Card> freeCards = cardRepository.findByFreeAndDeliveredToCompanyNullAndNfcUUIDNull(true, pageable);
        freeCards.forEach(c -> c.setDeliveredToCompany(companyId));
        List<Long> cardUUIDs = freeCards.stream().map(Card::getCardUUID).collect(Collectors.toList());
        cardRepository.save(freeCards);
        return cardUUIDs;
    }

    public User updateCompanyAdmin(String companyId, String userId, User user) {
        User existedUser = this.getCompanyAdmin(companyId, userId);
        User userWithThisEmail = userRepository.findByEmail(user.getEmail());
        if (userWithThisEmail != null) {
            if (!userWithThisEmail.getId().equals(userId)) {
                throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, user.getEmail());
            }
        }
        existedUser.setEmail(user.getEmail());
        existedUser.setName(user.getName());
        existedUser.setLastName(user.getLastName());
        return userRepository.save(existedUser);
    }

    public User getCompanyAdmin(String companyId, String userId) {
        User existedUser = userService.getUser(userId);
        if (!companyId.equals(existedUser.getCompanyId())) {
            throw new NotAllowedException(ApiErrorCode.USER_DOES_NOT_BELONG_TO_COMPANY, companyId, userId);
        }
        if (!existedUser.getRoles().contains(Role.ROLE_COMPANY.name())) {
            throw new NotAllowedException(ApiErrorCode.USER_IS_NOT_ADMIN_FOR_COMPANY, companyId, userId);
        }
        return existedUser;
    }

    public List<Long> removeFreeNfcCardsFromCompany(String companyId, long fromCard, long toCard) {
        if (fromCard > toCard) {
            throw new RuntimeException("start of rang can't be greater than end");
        }
        fromCard = fromCard - 1;
        toCard = toCard + 1;
        List<Card> freeNfcCardsDeliveredToCompany = cardRepository.findByCardUUIDBetweenAndDeliveredToCompanyAndFreeAndNfcUUIDNotNull(fromCard, toCard, companyId, true);
        freeNfcCardsDeliveredToCompany.forEach(c -> c.setDeliveredToCompany(null));
        cardRepository.save(freeNfcCardsDeliveredToCompany);
        List<Long> cardUUIDs = freeNfcCardsDeliveredToCompany.stream().map(c -> c.getCardUUID()).collect(Collectors.toList());
        return cardUUIDs;
    }

    public List<Long> removeFreeVirtualCardsFromCompany(String companyId, long fromCard, long toCard) {
        if (fromCard > toCard) {
            throw new RuntimeException("start of rang can't be greater than end");
        }
        fromCard = fromCard - 1;
        toCard = toCard + 1;
        List<Card> freeVirtualCardsDeliveredToCompany = cardRepository.findByCardUUIDBetweenAndDeliveredToCompanyAndFreeAndNfcUUIDNull(fromCard, toCard, companyId, true);
        freeVirtualCardsDeliveredToCompany.forEach(c -> c.setDeliveredToCompany(null));
        cardRepository.save(freeVirtualCardsDeliveredToCompany);
        List<Long> cardUUIDs = freeVirtualCardsDeliveredToCompany.stream().map(c -> c.getCardUUID()).collect(Collectors.toList());
        return cardUUIDs;
    }

    public Company updateCompanyRequisites(String companyId, CompanyRequisites companyRequisites) {
        Company company = companyService.getCompany(companyId);
        CompanyRequisites existedRequisites = company.getCompanyRequisites();
        existedRequisites.setCompanyFullName(companyRequisites.getCompanyFullName());
        existedRequisites.setUnp(companyRequisites.getUnp());
        existedRequisites.setBank(companyRequisites.getBank());
        existedRequisites.setBankAddress(companyRequisites.getBankAddress());
        existedRequisites.setBankCode(companyRequisites.getBankCode());
        existedRequisites.setPaymentAccount(companyRequisites.getPaymentAccount());
        existedRequisites.setContactEmail(companyRequisites.getContactEmail());
        existedRequisites.setLegalAddress(companyRequisites.getLegalAddress());
        existedRequisites.setDirectorName(companyRequisites.getDirectorName());
        existedRequisites.setDirectorWorkForm(companyRequisites.getDirectorWorkForm());
        return companyRepository.save(company);
    }
}
