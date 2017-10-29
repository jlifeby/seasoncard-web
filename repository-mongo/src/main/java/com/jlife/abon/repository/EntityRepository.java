package com.jlife.abon.repository;

import com.jlife.abon.entity.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * public interface to work with Entity.
 *
 * @author Dzmitry Misiuk
 */
@NoRepositoryBean
public interface EntityRepository<T extends Entity> extends MongoRepository<T, String>{

    /**
     * Returns all instances of the type those were created by the given creator.
     *
     * @param createdBy creator id
     * @return all relevant entities
     */
    List<T> findByCreatedBy(String createdBy);

    int countByCompanyId(String companyId);

}