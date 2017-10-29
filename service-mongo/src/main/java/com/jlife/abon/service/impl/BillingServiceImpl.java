package com.jlife.abon.service.impl;

import com.jlife.abon.TransactionType;
import com.jlife.abon.dto.PriceData;
import com.jlife.abon.entity.*;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.AccountRepository;
import com.jlife.abon.repository.TransactionRepository;
import com.jlife.abon.service.BillingService;
import com.jlife.abon.service.CompanyService;
import com.jlife.abon.service.SCConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Copyright © 2016 JLife Systems. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SCConfigurationService scConfigurationService;

    @Override
    public Account getAccountOrCreate(String companyId) {
        Company company = companyService.getCompany(companyId);
        List<Account> existingAccount = accountRepository.findAllByCompanyId(companyId);

        Optional<Account> accountForCurrencyOpt = existingAccount
                .stream()
                .filter(ac -> company.getCurrency().equals(ac.getCurrency()))
                .findFirst();

        if (accountForCurrencyOpt.isPresent()) {
            return accountForCurrencyOpt.get();
        } else {
            Account newAccount = new Account();
            newAccount.setCurrency(company.getCurrency());
            newAccount.setBalance(scConfigurationService.getInitialAccountBalance(company.getCountry()));
            newAccount.setCompanyId(companyId);
            return accountRepository.save(newAccount);
        }
    }

    @Override
    public Transaction doReplenishment(Transaction replenishment) {
        TransactionDetails details = replenishment.getDetails();
        Account account = accountRepository.findOne(replenishment.getAccountId());

        Assert.isTrue(replenishment.getType() == TransactionType.REPLENISHMENT, "should be replenishment type");
        Assert.notNull(details);
        Assert.isInstanceOf(ReplenishmentDetails.class, details);
        Assert.notNull(replenishment.getCurrency());
        Assert.notNull(replenishment.getValue());
        Assert.isTrue(replenishment.getValue().compareTo(BigDecimal.ZERO) > 0);
        if (account == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ACCOUNT_NOT_FOUND, replenishment.getAccountId());
        }
        Assert.isTrue(account.getCurrency() == replenishment.getCurrency(), "currency of transaction and account should be the same");

        account.setBalance(account.getBalance().add(replenishment.getValue()));

        Transaction storedTransaction = transactionRepository.save(replenishment);
        accountRepository.save(account);
        return storedTransaction;
    }

    @Override
    public void checkMoneyToSendSms(String text, String fromCompanyId) {
        Account account = getAccountOrCreate(fromCompanyId);
        Company company = companyService.getCompany(fromCompanyId);
        BigDecimal smsPrice = calculateSmsPrice(text, company);
        if (account.getBalance().compareTo(smsPrice) < 0) {
            throw new NotAllowedException(
                    ApiErrorCode.NOT_ENOUGH_MONEY_ON_ACCOUNT, account.getId(),
                    smsPrice.toPlainString(),
                    account.getBalance().toPlainString());
        }
    }

    private BigDecimal calculateSmsPrice(String text, Company company) {
        PriceData smsUnitPrice = scConfigurationService.getSmsPrice(company.getCountry());
        int countOfUnit = (int) Math.ceil((double) text.length() / SMS_UNIT_SIZE);
        return smsUnitPrice.getValue().multiply(new BigDecimal(countOfUnit));
    }

    private BigDecimal calculateSmsGroupPrice(String text, Company company, long numberOfMessages) {
        return calculateSmsPrice(text, company).multiply(BigDecimal.valueOf(numberOfMessages));
    }

    @Override
    public Transaction doPayment(Transaction payment) {
        TransactionDetails details = payment.getDetails();
        Account account = accountRepository.findOne(payment.getAccountId());

        Assert.isTrue(payment.getType() == TransactionType.PAYMENT, "should be payment type");
        Assert.notNull(details);
        Assert.isInstanceOf(PaymentDetails.class, details);
        Assert.notNull(payment.getCurrency());
        Assert.notNull(payment.getValue());
        Assert.isTrue(payment.getValue().compareTo(BigDecimal.ZERO) > 0);
        if (account == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ACCOUNT_NOT_FOUND, payment.getAccountId());
        }
        Assert.isTrue(account.getCurrency() == payment.getCurrency(), "currency of transaction and account should be the same");

        if (account.getBalance().compareTo(payment.getValue()) < 0) {
            throw new NotAllowedException(
                    ApiErrorCode.NOT_ENOUGH_MONEY_ON_ACCOUNT, account.getId(),
                    payment.getValue().toPlainString(),
                    account.getBalance().toPlainString());
        }

        account.setBalance(account.getBalance().subtract(payment.getValue()));

        Transaction storedTransaction = transactionRepository.save(payment);
        accountRepository.save(account);
        return storedTransaction;
    }

    @Override
    public Transaction doPaymentBasedOnSms(SmsMessage smsMessage,  String fromCompanyId) {
        Account account = getAccountOrCreate(fromCompanyId);
        Company company = companyService.getCompany(fromCompanyId);
        BigDecimal smsPrice =calculateSmsPrice(smsMessage.getText(), company);
        Transaction payment = new Transaction();
        payment.setCompanyId(fromCompanyId);
        payment.setCurrency(company.getCurrency());
        payment.setType(TransactionType.PAYMENT);
        payment.setValue(smsPrice);
        payment.setAccountId(account.getId());
        SmsPayment smsPayment = new SmsPayment();
        smsPayment.setSmsId(smsMessage.getId());
        payment.setDetails(smsPayment);
        return doPayment(payment);
    }

    @Override
    public Transaction doPaymentBasedOnSmsGroup(SmsMessageGroup smsGroup, String fromCompanyId) {
        Account account = getAccountOrCreate(fromCompanyId);
        Company company = companyService.getCompany(fromCompanyId);
        BigDecimal smsPrice = calculateSmsGroupPrice(smsGroup.getText(), company, smsGroup.getSmsIds().size());
        Transaction payment = new Transaction();
        payment.setCompanyId(fromCompanyId);
        payment.setCurrency(company.getCurrency());
        payment.setType(TransactionType.PAYMENT);
        payment.setValue(smsPrice);
        payment.setAccountId(account.getId());
        SmsGroupPayment smsGroupPayment = new SmsGroupPayment();
        smsGroupPayment.setSmsGroupId(smsGroup.getId());
        payment.setDetails(smsGroupPayment);
        return doPayment(payment);
    }
}
