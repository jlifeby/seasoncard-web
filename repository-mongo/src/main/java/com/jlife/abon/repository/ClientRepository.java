package com.jlife.abon.repository;

import com.jlife.abon.entity.Client;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
public interface ClientRepository extends EntityRepository<Client>, ClientRepositoryCustom {
    Stream<Client> findByCompanyId(String companyId);

    Page<Client> findByCompanyId(String companyId, Pageable pageable);

    Client findOneByUserIdAndCompanyId(String userId, String companyId);

    Client findOneByCompanyIdAndCardUUID(String companyId, Long cardUUID);

    List<Client> findByCompanyIdAndCreatedDateBetween(String companyId, DateTime startDate, DateTime endDate);

    List<Client> findByCreatedDateBetween(DateTime startDate, DateTime endDate);

    List<Client> findByCompanyId(String companyId, TextCriteria textCriteria, Pageable pageable);

    List<Client> findOneByCompanyIdAndCardUUIDIn(String companyId, List<Long> uuids);

    List<Client> findByUserId(String userId);

    Page<Client> findByTagsAndCompanyId(String tag, String companyId, Pageable pageable);

    Client findOneByCompanyIdAndInternalId(String companyId, String internalId);

    List<Client> findByCardUUID(Long cardUUID);

    Stream<Client> findByCompanyIdOrderByLastNameAsc(String companyId);
}
