package com.jlife.abon.repository;

import com.jlife.abon.entity.Promotion;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface PromotionRepository extends EntityRepository<Promotion> {

    List<Promotion> findByCompanyId(String companyId);

    List<Promotion> findByCompanyIdAndActive(String companyId, boolean active);

    List<Promotion> findByActiveAndPublishedAndCompanyIdIn(boolean active,
                                                           boolean published,
                                                           Collection<String> publishedCompanyIds);

    List<Promotion> findByActiveAndPublished(boolean active, boolean published);

    Page<Promotion> findByActiveAndPublishedAndTermless(boolean active, boolean published, boolean termless, Pageable pageable);

    Page<Promotion> findByActiveAndPublishedAndTermlessAndEndDateGreaterThan(boolean active, boolean published, boolean termless, DateTime date, Pageable pageable);

    List<Promotion> findByActiveAndPublishedAndTermlessAndCompanyId(boolean active, boolean published, boolean termless, String companyId);

    List<Promotion> findByActiveAndPublishedAndTermlessAndEndDateGreaterThanAndCompanyId(boolean active, boolean published, boolean termless, DateTime dateTime, String companyId);

    List<Promotion> findByActiveAndTermlessAndCompanyId(boolean active, boolean termless, String companyId);

    List<Promotion> findByActiveAndTermlessAndEndDateGreaterThanAndCompanyId(boolean active, boolean termless, DateTime dateTime, String companyId);
}
