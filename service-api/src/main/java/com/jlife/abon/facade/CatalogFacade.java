package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface CatalogFacade {
    List<CompanyData> getPublishedCompanies();
}
