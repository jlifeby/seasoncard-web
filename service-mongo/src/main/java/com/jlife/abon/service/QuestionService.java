package com.jlife.abon.service;

import com.jlife.abon.entity.Question;
import com.jlife.abon.entity.QuestionGroup;

import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public interface QuestionService {

    Question saveQuestion(Question question);

    QuestionGroup saveQuestionGroup(QuestionGroup questionGroup);
    
    List<QuestionGroup> getGroups();
    
    QuestionGroup getGroup(String groupId);
    
    Question getQuestion(String questionId);
    
    Map<QuestionGroup, List<Question>> getMapForIndexPage();
    
    List<Question> getQuestions(String groupId);
    
    List<Question> findQuestions(String text, int page, int size);

}
