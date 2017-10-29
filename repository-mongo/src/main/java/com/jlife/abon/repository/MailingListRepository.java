package com.jlife.abon.repository;

import com.jlife.abon.entity.MailingList;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Dzmitry Khralovich
 */
@NoRepositoryBean
public interface MailingListRepository<T extends MailingList> extends EntityRepository<T> {

}