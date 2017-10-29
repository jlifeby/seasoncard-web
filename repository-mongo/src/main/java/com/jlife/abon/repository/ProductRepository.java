package com.jlife.abon.repository;

import com.jlife.abon.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface ProductRepository extends EntityRepository<Product> {

    List<Product> findByCompanyIdAndActive(String companyId, boolean active);

    List<Product> findByActive(boolean active);

    List<Product> findByCompanyId(String companyId);

    List<Product> findByActiveAndPublishedAndCompanyIdIn(boolean active, boolean published, Collection<String> publishedCompanyIds);

    Page<Product> findByActiveAndPublishedAndCompanyIdIn(boolean active,
                                                         boolean published,
                                                         Collection<String> publishedCompanyIds,
                                                         Pageable pageable);

    Page<Product> findByCompanyIdAndActive(String companyId, boolean active, Pageable pageable);

    Product findOneByCompanyIdAndPublishedAndActive(String companyId, boolean published, boolean active, Sort sort);
}
