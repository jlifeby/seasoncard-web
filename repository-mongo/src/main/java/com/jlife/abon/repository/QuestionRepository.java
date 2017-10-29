package com.jlife.abon.repository;

import com.jlife.abon.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface QuestionRepository extends EntityRepository<Question> {

    List<Question> findByGroup(String group);

    List<Question> findByGroup(String group, Pageable pageable);

    List<Question> findBy(TextCriteria criteria, Pageable pageable);
}
