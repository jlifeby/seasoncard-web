package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Company;
import com.jlife.abon.facade.CompanyEmailFacade;
import com.jlife.abon.facade.EnrollFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class EnrollFacadeImpl extends AbstractFacade implements EnrollFacade {

    @Autowired
    private CompanyEmailFacade companyEmailFacade;

    @Override
    public AbonnementData enrollAbonnement(Long cardUUID, String companyId, AbonnementData abonnementData) {
        Abonnement purchasedAbon = enrollService.enrollAbonnement(cardUUID, companyId,
                dataMapper.fromAbonnementData(abonnementData));
        productService.increaseSelling(abonnementData.getProductId());

        Company company = companyService.getCompany(companyId);
        if (company.getCompanySettings().isAbonSoldEmailNotification() &&
                StringUtils.isNotBlank(company.getCompanySettings().getNotificationEmail())) {
            companyEmailFacade.sendEnrollNotification(cardUUID, companyId, purchasedAbon.getId());
        }

        return dataMapper.toAbonnementData(purchasedAbon);
    }
}
