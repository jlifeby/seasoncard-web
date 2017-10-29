package com.jlife.abon.repository;

import com.jlife.abon.entity.User;

import java.util.List;

/**
 * Repository to work with User Entity.
 *
 * @author Dzmitry Misiuk
 */
public interface UserRepository extends EntityRepository<User> {

    User findByEmail(String email);

    User findByConfirm(String confirm);

    List<User> findByCompanyId(String companyId);

    User findOneByPhone(String phone);

    User findOneByCardUUID(Long cardUUID);

    User findByRecoveringId(String recoveringId);

    List<User> findByCompanyIdAndRolesIn(String companyId, List<String> userRoles);

    User findOneByPhoneAndCardUUID(String phone, Long cardUUID);
}