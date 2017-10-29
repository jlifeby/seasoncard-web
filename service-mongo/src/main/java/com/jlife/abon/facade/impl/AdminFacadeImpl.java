package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.CompanyRequisitesData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.User;
import com.jlife.abon.facade.AdminFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class AdminFacadeImpl extends AbstractFacade implements AdminFacade {

    @Override
    public CompanyData createCompany(CompanyData companyData) {
        Company savedCopmany = adminService.createCompany(dataMapper.fromCompanyData(companyData));
        return dataMapper.toCompanyData(savedCopmany);
    }

    @Override
    public CompanyData updateCompany(String companyId, CompanyData company) {
        Company updatedCopmany = adminService.updateCompany(companyId, dataMapper.fromCompanyData(company));
        return dataMapper.toCompanyData(updatedCopmany);
    }

    @Override
    public List<Long> addNfcCardsToCompany(String companyId, long fromCard, long toCard) {
        return adminService.addNfcCardsToCompany(companyId, fromCard, toCard);
    }

    @Override
    public List<Long> addVirtualCardsToCompany(String companyId, int count) {
        return adminService.addVirtualCardsToCompany(companyId, count);
    }

    @Override
    public UserData addCompanyAdmin(String companyId, UserData user) {
        User companyAdmin = adminService.createCompanyAdmin(companyId, dataMapper.fromUserData(user));
        return dataMapper.toUserData(companyAdmin);
    }

    @Override
    public UserData updateCompanyAdmin(String companyId, String userId, UserData user) {
        User updatedCompanyAdmin = adminService.updateCompanyAdmin(companyId, userId, dataMapper.fromUserData(user));
        return dataMapper.toUserData(updatedCompanyAdmin);
    }

    @Override
    public UserData getCompanyAdmin(String companyId, String userId) {
        User companyAdmin = adminService.getCompanyAdmin(companyId, userId);
        return dataMapper.toUserData(companyAdmin);
    }

    @Override
    public List<UserData> getCompanyAdmins(String companyId) {
        List<User> companyAdmins = adminService.getCompanyAdmins(companyId);
        return dataMapper.toUserDataList(companyAdmins);
    }

    @Override
    public void sendCompanyAdminCredentials(String companyId, String userId) {
        adminService.sendCompanyAdminCredentials(companyId, userId);
    }

    @Override
    public List<Long> removeFreeNfcCardsFromCompany(String companyId, long fromCard, long toCard) {
        return adminService.removeFreeNfcCardsFromCompany(companyId, fromCard, toCard);
    }

    @Override
    public List<Long> removeFreeVirtualCardsFromCompany(String companyId, long fromCard, long toCard) {
        return adminService.removeFreeVirtualCardsFromCompany(companyId, fromCard, toCard);
    }

    @Override
    public CompanyData updateCompanyRequisites(String companyId, CompanyRequisitesData companyRequisites) {
        Company company = adminService.updateCompanyRequisites(companyId, dataMapper.fromCompanyRequisitesData(companyRequisites));
        return dataMapper.toCompanyData(company);
    }
}
