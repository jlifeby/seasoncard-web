package com.jlife.abon.repository;

import com.jlife.abon.entity.SmsMessage;
import com.jlife.abon.enumeration.SmsType;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Repository
public interface SmsMessageRepository extends EntityRepository<SmsMessage> {

    int countByPhoneAndSmsTypeAndSentDateBetween(String phone, SmsType smsType, DateTime endDate, DateTime startDate);

    Page<SmsMessage> findByUserIdAndFromCompanyId(String userId, String companyId, Pageable pageable);

    List<SmsMessage> findByFromCompanyId(String companyId);

    Page<SmsMessage> findByFromCompanyId(String companyId, Pageable pageable);
}
