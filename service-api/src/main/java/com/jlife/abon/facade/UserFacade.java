package com.jlife.abon.facade;

import com.jlife.abon.dto.UserData;
import com.jlife.abon.dto.UserSettingsData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface UserFacade {
    UserData getUserWithCompany(String userId);

    List<UserData> getAllUsers();

    UserData updateOwnProfileAsUser(UserData user, String userId);

    UserData updateOwnSettingsAsUser(UserSettingsData userSettings, String userId);

    UserData findByEmail(String email);

    UserData findOneByCardUUID(Long cardUUID);

    UserData save(UserData user);

    UserData findById(@NotNull String userId);
}
