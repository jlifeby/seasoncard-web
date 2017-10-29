package com.jlife.abon.facade;

import com.jlife.abon.dto.ClientGroupData;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ClientGroupFacade {
    List<ClientGroupData> getClientsGroupsByCompany(String companyId);

    ClientGroupData getClientGroupByCompanyAndProduct(String companyId, String productId);
}
