package com.jlife.abon.service;

import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.PhoneChanging;
import com.jlife.abon.entity.User;
import com.jlife.abon.enumeration.Role;
import com.jlife.abon.enumeration.UserState;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.interfaces.Existence;
import com.jlife.abon.service.sms.SmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.jlife.abon.util.SecurityUtilKt.encodeBCrypt;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class UserService extends AbstractService {

    private static final int LIMIT_RECOVERING_PASSWORD_WITH_PHONE = 3;

    @Autowired
    @Qualifier(DEFAULT_SMS_SERVICE)
    private SmsService smsService;

    public User getUser(String userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new ResourceNotFoundException(ApiErrorCode.USER_NOT_FOUND, userId);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException(ApiErrorCode.USER_NOT_FOUND, email);
        }
        return user;
    }

    /**
     * Returns id to create confirmation link
     *
     * @param userId
     * @param newEmail
     * @return
     */
    public String setNewEmail(String userId, String newEmail) {
        User userWithThisEmail = userRepository.findByEmail(newEmail);
        if (userWithThisEmail != null) {
            if (userWithThisEmail.getId().equals(userId)) {
                throw new NotAllowedException(ApiErrorCode.YOU_ALREADY_USE_THIS_EMAIL, newEmail);
            }
            throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, newEmail);
        }
        User user = this.getUser(userId);
        user.setNewEmail(newEmail);
        String confirmationId = UUID.randomUUID().toString();
        user.setConfirm(confirmationId);
        Set<UserState> userStates = user.getUserStates();
        userStates.add(UserState.WAITING_FOR_CONFIRM_EMAIL);
        user.setUserStates(userStates);
        userRepository.save(user);
        return confirmationId;
    }

    public void confirmChangingEmail(String confirmationId) {
        User user = userRepository.findByConfirm(confirmationId);
        if (user == null) {
            throw new ResourceNotFoundException(ApiErrorCode.OBJECT_TO_CONFIRM_DOS_NOT_EXIST, confirmationId);
        }
        String newEmail = user.getNewEmail();
        User userWithThisEmail = userRepository.findByEmail(newEmail);
        if (userWithThisEmail != null) {
            throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, newEmail);
        }
        Set<UserState> userStates = user.getUserStates();
        userStates.remove(UserState.WAITING_FOR_CONFIRM_EMAIL);
        userStates.add(UserState.CONFIRMED_EMAIL);
        user.setUserStates(userStates);
        user.setConfirm(null);
        user.setEmail(newEmail);
        user.setNewEmail(null);
        userRepository.save(user);
    }

    /**
     * Update password for user
     *
     * @param userId
     * @param newPassword
     * @return
     */
    public void setNewPasswordByUser(String userId, String newPassword) {
        User user = getUser(userId);
        if (!user.hasAlreadyConfirmedEmail()) {
            throw new NotAllowedException(ApiErrorCode.USER_SHOULD_CONFIRM_EMAIL_BEFORE_CHANGING_PASSWORD, userId);
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
        Set<UserState> userStates = user.getUserStates();
        user.setPassword(hashedPassword);
        if (!userStates.contains(UserState.CHANGED_INIT_PASSWORD)) {
            userStates.add(UserState.CHANGED_INIT_PASSWORD);
            user.setUserStates(userStates);
        }
        userRepository.save(user);
    }

    /**
     * Update password for user by external admin, business logic
     *
     * @param userId
     * @param newPassword
     * @return
     */
    public void setNewPasswordByExternal(String userId, String newPassword) {
        User user = getUser(userId);
        String hashedPassword = encodeBCrypt(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public boolean authenticate(String userId, String currentPassword) {
        User user = userRepository.findOne(userId);
        String oldPassword = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(currentPassword, oldPassword);
    }

    /**
     * Returns id to recover password link
     *
     * @param email
     * @return
     */
    public String setRecoveringId(String email) {
        User user = this.getUserByEmail(email);
        String recoveringId = UUID.randomUUID().toString();
        user.setRecoveringId(recoveringId);
        userRepository.save(user);
        return recoveringId;
    }

    /**
     * Sets new password for user using secret recovering id
     *
     * @param recoveringId
     * @param newPassword
     * @return
     */
    public void setNewPasswordByRecovering(String recoveringId, String newPassword) {
        User user = userRepository.findByRecoveringId(recoveringId);
        if (user == null) {
            throw new ResourceNotFoundException(ApiErrorCode.RECOVERING_IS_NOT_ACTIVE_OR_DOS_NOT_EXIST, recoveringId);
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(hashedPassword);
        user.setRecoveringId(null);
        userRepository.save(user);
    }

    /**
     * Checks for existing recovering id
     *
     * @param recoveringId
     * @return
     */
    public boolean isExistRecoveringId(String recoveringId) {
        User user = userRepository.findByRecoveringId(recoveringId);
        return user != null;
    }

    /**
     * Mark user as accepted user agreement.
     *
     * @param userId
     */
    public void acceptAgreementByUser(String userId) {
        User user = userRepository.findOne(userId);
        Set<UserState> userStates = user.getUserStates();
        if (!userStates.contains(UserState.ACCEPTED_AGREEMENT)) {
            userStates.add(UserState.ACCEPTED_AGREEMENT);
            user.setUserStates(userStates);
        }
        userRepository.save(user);
    }

    public User getUserByCardUUD(Long cardUUID) {
        User user = userRepository.findOneByCardUUID(cardUUID);
        if (user == null) {
            throw new ResourceNotFoundException(ApiErrorCode.USER_NOT_FOUND_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        return user;
    }

    public void recoverPasswordWithPhone(String phone, Long cardUUID) {
        if (smsService.countRecoveringRequestsForToday(phone) >= LIMIT_RECOVERING_PASSWORD_WITH_PHONE) {
            throw new NotAllowedException(ApiErrorCode.ACHIEVED_LIMIT_OF_RECOVERING_PASSWORD_FOR_PHONE, phone,
                    String.valueOf(LIMIT_RECOVERING_PASSWORD_WITH_PHONE));
        }
        User existedUser = userRepository.findOneByPhoneAndCardUUID(phone, cardUUID);
        if (existedUser == null) {
            throw new ResourceNotFoundException(ApiErrorCode.USER_NOT_FOUND_WITH_PHONE_AND_CARD_UUID, phone, String.valueOf(cardUUID));
        }
        String password = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        existedUser.setPassword(hashedPassword);
        userRepository.save(existedUser);

        smsService.sendRecoveringPasswordBySms(phone, existedUser.getId(), cardUUID, password);
    }

    public User updateUserSettings(String userId, boolean allowedSmsReceiving, boolean allowedEmailReceiving) {
        User user = getUser(userId);
        user.setAllowedEmailReceiving(allowedEmailReceiving);
        user.setAllowedSmsReceiving(allowedSmsReceiving);
        return userRepository.save(user);
    }

    public User setNewPhone(long cardUUID, String newPhone, String newPassword, String companyId) {
        User user = getUserByCardUUD(cardUUID);
        PhoneChanging phoneChanging = new PhoneChanging();
        phoneChanging.setOldPhone(user.getPhone());

        Client client = clientRepository.findOneByCompanyIdAndCardUUID(companyId, cardUUID);
        if (client == null) {
            throw new NotAllowedException(ApiErrorCode.CLIENT_NOT_FOUND_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        user.setPhone(newPhone);
        String hashedPassword = encodeBCrypt(newPassword);
        user.setPassword(hashedPassword);

        List<PhoneChanging> phoneChangings = user.getPhoneChangings();
        phoneChanging.setNewPhone(newPhone);
        phoneChangings.add(phoneChanging);
        phoneChanging.setCreatedBy(this.auditor.getCurrentAuditor());
        phoneChanging.setCreatedDate(new DateTime());
        user.setPhoneChangings(phoneChangings);

        return userRepository.save(user);
    }

    @NotNull
    public User selfRegisterFromClient(@NotNull Client client) {
        Assert.notNull(client.getName());
        Assert.notNull(client.getPhone());
        Assert.notNull(client.getCompanyId());
        if (client.getEmail() != null && !this.isFreeEmailForUser(client.getEmail())) {
            throw new NotAllowedException(ApiErrorCode.USER_ALREADY_EXISTS_WITH_EMAIL, client.getEmail());
        }

        Card nextFreeVirtualCard = cardService.occupyFreeVirtualCardForSelfRegister(client.getCompanyId());

        User user = new User();
        user.setName(client.getName());
        user.setLastName(client.getLastName());
        user.setPhone(client.getPhone());
        user.setNewEmail(client.getEmail());
        user.setLogoPath(client.getLogoPath());
        user.setPotential(true);
        user.setCardUUID(nextFreeVirtualCard.getCardUUID());
        user.getRoles().add(Role.ROLE_USER.name());
        user.setStatus(Existence.ACTIVE_STATUS);
        User storedUser = userRepository.save(user);

        nextFreeVirtualCard.setUserId(storedUser.getId());
        nextFreeVirtualCard.setInitializingCompany(client.getCompanyId());
        cardRepository.save(nextFreeVirtualCard);

        return storedUser;
    }


    public boolean isFreeEmailForUser(String email) {
        User userWithThisEmail = userRepository.findByEmail(email);
        return userWithThisEmail == null;
    }

    @NotNull
    public User makeUserReal(long cardUUID) {
        User user = getUserByCardUUD(cardUUID);
        user.setPotential(false);
        return userRepository.save(user);
    }
}
