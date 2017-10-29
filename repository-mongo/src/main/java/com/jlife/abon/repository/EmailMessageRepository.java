package com.jlife.abon.repository;

import com.jlife.abon.entity.EmailMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Repository
public interface EmailMessageRepository extends EntityRepository<EmailMessage> {

    List<EmailMessage> findByUserIdAndFromCompanyId(String userId, String companyId);

    List<EmailMessage> findByFromCompanyId(String companyId);
}
