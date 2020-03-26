package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Question;

public interface QuestionService {
    EntityList<Question> getQuestions(QueryParameters params);
    Question getQuestion(String questionId);
    Question createQuestion(Question question);
    Question updateQuestion(String questionId, Question question);
    void removeQuestion(String questionId);
}
