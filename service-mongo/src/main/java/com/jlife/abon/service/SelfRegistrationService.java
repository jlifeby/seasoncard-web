package com.jlife.abon.service;

import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.User;
import com.jlife.abon.enumeration.Role;
import com.jlife.abon.enumeration.UserState;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class SelfRegistrationService extends AbstractService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceService sequenceService;

    public Company selfRegisterCompany(Company company) {
        company.setId(null);
        int contractId = sequenceService.getNextContractId();
        company.setContractId(contractId);
        company.setPublished(false);
        return companyRepository.save(company);
    }

    public User registerAdminForCompany(User user, Company company) {
        user.setCompanyId(company.getId());
        user.setUserStates(new HashSet<>(Arrays.asList(UserState.ACCEPTED_AGREEMENT, UserState.CONFIRMED_EMAIL)));
        user.getRoles().add(Role.ROLE_COMPANY.name());
        User userWithThisEmail = userRepository.findByEmail(user.getEmail());
        if (userWithThisEmail != null) {
            throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, user.getEmail());
        }
        return userRepository.save(user);
    }


    public boolean isFreeCardsAvailableToRegister(int freeVirtualCardAmountRequired) {
        long freeVirtualCardCountAvailable = cardRepository.countByFreeAndDeliveredToCompanyNullAndNfcUUIDNull(true);
        return freeVirtualCardAmountRequired <= freeVirtualCardCountAvailable;
    }
}
