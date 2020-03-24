package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.QuestionEntity;

import java.util.List;

public interface QuestionService {
    List<QuestionEntity> getQuestions(QueryParameters params);
    QuestionEntity getQuestion(String questionId);
    QuestionEntity createQuestion(QuestionEntity question);
    QuestionEntity updateQuestion(String questionId, QuestionEntity question);
    void removeQuestion(String questionId);
}
