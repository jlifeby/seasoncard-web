package com.jlife.abon.facade;

import com.jlife.abon.dto.ClientData;
import org.jetbrains.annotations.NotNull;

/**
 * Copyright © 2015-2017 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface PotentialClientFacade {

    @NotNull
    ClientData selfRegisterClient(@NotNull ClientData clientData, @NotNull String companyId);

    @NotNull
    ClientData makeClientReal(long cardUUID, @NotNull String companyId);

    @NotNull
    ClientData makeClientRealWithReplacing(long cardUUID, @NotNull String companyId, @NotNull Long newCardUUID);

}
