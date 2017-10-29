package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.UserData;
import com.jlife.abon.dto.UserSettingsData;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.User;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.UserFacade;
import com.jlife.abon.repository.AbonnementRepository;
import com.jlife.abon.repository.CompanyRepository;
import com.jlife.abon.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class UserFacadeImpl extends AbstractFacade implements UserFacade {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AbonnementRepository abonnementRepository;

    @Override
    public UserData getUserWithCompany(String userId) {
        User user = userRepository.findOne(userId);
        if (user != null && user.getCompanyId() != null) {
            Company company = companyRepository.findOne(user.getCompanyId());
            user.setCompany(company);
        }
        return dataMapper.toUserData(user);
    }

    @Override
    public List<UserData> getAllUsers() {
        List<User> users = userRepository.findAll();
        return dataMapper.toUserDataList(users);
    }

    @Override
    public UserData updateOwnProfileAsUser(UserData user, String userId) {
        User existedUser = userRepository.findOne(userId);
        if (existedUser == null) {
            throw new ResourceNotFoundException(ApiErrorCode.USER_NOT_FOUND, userId);
        }
        existedUser.setName(user.getName());
        existedUser.setLastName(user.getLastName());
        existedUser.setBirthday(user.getBirthday());
        existedUser.setLogoPath(user.getLogoPath());
        existedUser.setLogoMediaId(user.getLogoMediaId());
        existedUser.setVehicleRegistrationPlate(user.getVehicleRegistrationPlate());
        existedUser.setCountry(user.getCountry());
        if (StringUtils.isBlank(existedUser.getPhone())) {
            existedUser.setPhone(user.getPhone());
        }
        User savedUser = userRepository.save(existedUser);
        return dataMapper.toUserData(savedUser);
    }

    @Override
    public UserData updateOwnSettingsAsUser(UserSettingsData userSettings, String userId) {
        User user = this.userService.updateUserSettings(userId, userSettings.isAllowedSmsReceiving(), userSettings.isAllowedEmailReceiving());
        return dataMapper.toUserData(user);
    }

    @Override
    public UserData findByEmail(String email) {
        User byEmail = userRepository.findByEmail(email);
        return dataMapper.toUserData(byEmail);
    }

    @Override
    public UserData findOneByCardUUID(Long cardUUID) {
        User oneByCardUUID = userRepository.findOneByCardUUID(cardUUID);
        return dataMapper.toUserData(oneByCardUUID);
    }

    @Override
    public UserData save(UserData user) {
        User savedUser = userRepository.save(dataMapper.fromUserData(user));
        return dataMapper.toUserData(savedUser);
    }

    @Override
    public UserData findById(@NotNull String userId) {
        User oneById = userRepository.findOne(userId);
        return dataMapper.toUserData(oneById);
    }
}
