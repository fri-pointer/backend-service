package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.AnswerEntity;

import java.util.List;

public interface AnswerService {
    List<AnswerEntity> getAnswers(QueryParameters params);
    AnswerEntity getAnswer(String answerId);
    AnswerEntity createAnswer(AnswerEntity answer);
    AnswerEntity updateAnswer(String answerId, AnswerEntity answer);
    void removeAnswer(String answerId);
}
