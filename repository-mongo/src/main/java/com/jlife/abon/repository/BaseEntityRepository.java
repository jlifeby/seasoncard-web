package com.jlife.abon.repository;

import com.jlife.abon.entity.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface to work with BaseEntity repository.
 *
 * @author Dzmitry Misiuk
 */
@NoRepositoryBean
public interface BaseEntityRepository<T> extends PagingAndSortingRepository<BaseEntity, String> {
}