package com.jlife.abon.service;

import com.jlife.abon.entity.Entity;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.EntityRepository;
import org.springframework.util.Assert;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface EntityAbstractService<T extends Entity> {

    default T getActiveOneWithOwnerCompanyId(String id, String companyId) {
        Assert.notNull(id);
        Assert.notNull(companyId);

        T entity = getOneWithOwnerCompanyId(id, companyId);
        if (!entity.isActive()) {
            throw new NotAllowedException(ApiErrorCode.ENTITY_IS_NOT_ACTIVE, getBaseEntityName(), id);
        }
        return entity;
    }

    default T getActiveOne(String id) {
        Assert.notNull(id);

        T entity = getOne(id);
        if (!entity.isActive()) {
            throw new NotAllowedException(ApiErrorCode.ENTITY_IS_NOT_ACTIVE, getBaseEntityName(), id);
        }
        return entity;
    }

    default T getOneWithOwnerCompanyId(String id, String companyId) {
        Assert.notNull(id);
        Assert.notNull(companyId);

        T entity = getOne(id);
        if (!companyId.equals(entity.getCompanyId())) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_ENTITY, companyId, getBaseEntityName(), id);
        }
        return entity;
    }

    default T getOne(String id) {
        Assert.notNull(id);
        T entity = getBaseRepository().findOne(id);
        if (entity == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ENTITY_NOT_FOUND, getBaseEntityName(), id);
        }
        return entity;
    }


    default EntityRepository<T> getBaseRepository() {
        throw new UnsupportedOperationException();
    }

    default String getBaseEntityName() {
        throw new UnsupportedOperationException();
    }
}
