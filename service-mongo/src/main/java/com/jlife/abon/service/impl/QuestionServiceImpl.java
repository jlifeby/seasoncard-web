package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Question;
import com.jlife.abon.entity.QuestionGroup;
import com.jlife.abon.repository.QuestionGroupRepository;
import com.jlife.abon.repository.QuestionRepository;
import com.jlife.abon.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionGroupRepository groupRepository;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public QuestionGroup saveQuestionGroup(QuestionGroup group) {
        return groupRepository.save(group);
    }

    @Override
    public List<QuestionGroup> getGroups() {
        return groupRepository.findAll();
    }

    @Override
    public QuestionGroup getGroup(String groupId) {
        return groupRepository.findOne(groupId);
    }

    @Override
    public Question getQuestion(String questionId) {
        return questionRepository.findOne(questionId);
    }

    @Override
    public Map<QuestionGroup, List<Question>> getMapForIndexPage() {
        Map<QuestionGroup, List<Question>> maps = new HashMap<QuestionGroup, List<Question>>();
        List<QuestionGroup> groups = this.getGroups();
        Pageable pageable = new PageRequest(0, 5);
        for (QuestionGroup group : groups) {
            List<Question> questions = questionRepository.findByGroup(group.getId(), pageable);
            maps.put(group, questions);
        }
        return maps;
    }

    @Override
    public List<Question> getQuestions(String groupId) {
        return questionRepository.findByGroup(groupId);
    }

    @Override
    public List<Question> findQuestions(String text, int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(text);
        return questionRepository.findBy(textCriteria, pageable);
    }
}
