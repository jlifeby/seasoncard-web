package com.jlife.abon.repository;

import com.jlife.abon.entity.SmsMessageGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsMessageGroupRepository extends EntityRepository<SmsMessageGroup> {

    List<SmsMessageGroup> findByCompanyId(String companyId);

    Page<SmsMessageGroup> findByCompanyId(String companyId, Pageable pageable);

}
