package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Answer;
import io.fripointer.persistence.AnswerEntity;
;

public interface AnswerService {
    EntityList<Answer> getAnswers(QueryParameters params);
    Answer getAnswer(String answerId);
    Answer createAnswer(Answer answer);
    Answer updateAnswer(String answerId, Answer answer);
    void removeAnswer(String answerId);
}
