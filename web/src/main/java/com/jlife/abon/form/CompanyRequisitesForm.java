package com.jlife.abon.form;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.CompanyRequisitesData;

import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class CompanyRequisitesForm implements Serializable {

    private String companyId;
    private String companyFullName;
    private String legalAddress;
    private String unp;
    private String directorName;
    private String directorWorkForm;
    private String contactEmail;
    private String bank;
    private String bankCode;
    private String bankAddress;
    private String paymentAccount;

    public String getCompanyFullName() {
        return companyFullName;
    }

    public void setCompanyFullName(String companyFullName) {
        this.companyFullName = companyFullName;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDirectorWorkForm() {
        return directorWorkForm;
    }

    public void setDirectorWorkForm(String directorWorkForm) {
        this.directorWorkForm = directorWorkForm;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public static CompanyRequisitesForm fromCompany(CompanyData company) {
        CompanyRequisitesData companyRequisites = company.getCompanyRequisites();
        CompanyRequisitesForm newForm = new CompanyRequisitesForm();
        newForm.setCompanyId(company.getId());
        newForm.setCompanyFullName(companyRequisites.getCompanyFullName());
        newForm.setUnp(companyRequisites.getUnp());
        newForm.setBank(companyRequisites.getBank());
        newForm.setBankAddress(companyRequisites.getBankAddress());
        newForm.setBankCode(companyRequisites.getBankCode());
        newForm.setPaymentAccount(companyRequisites.getPaymentAccount());
        newForm.setContactEmail(companyRequisites.getContactEmail());
        newForm.setLegalAddress(companyRequisites.getLegalAddress());
        newForm.setDirectorName(companyRequisites.getDirectorName());
        newForm.setDirectorWorkForm(companyRequisites.getDirectorWorkForm());
        return newForm;
    }

    public CompanyRequisitesData toCompanyRequisites() {
        CompanyRequisitesData companyRequisites = new CompanyRequisitesData();
        companyRequisites.setCompanyFullName(this.getCompanyFullName());
        companyRequisites.setUnp(this.getUnp());
        companyRequisites.setBank(this.getBank());
        companyRequisites.setBankAddress(this.getBankAddress());
        companyRequisites.setBankCode(this.getBankCode());
        companyRequisites.setPaymentAccount(this.getPaymentAccount());
        companyRequisites.setContactEmail(this.getContactEmail());
        companyRequisites.setLegalAddress(this.getLegalAddress());
        companyRequisites.setDirectorName(this.getDirectorName());
        companyRequisites.setDirectorWorkForm(this.getDirectorWorkForm());
        return companyRequisites;
    }
}

