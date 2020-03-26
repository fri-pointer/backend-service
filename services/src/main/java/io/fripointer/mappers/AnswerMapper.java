package io.fripointer.mappers;

import io.fripointer.lib.Answer;
import io.fripointer.persistence.AnswerEntity;

public class AnswerMapper {

    public static Answer fromEntity(AnswerEntity answerEntity){
        Answer answer = new Answer();
        answer.setId(answerEntity.getId());
        answer.setTimestamp(answerEntity.getTimestamp());
        answer.setContent(answerEntity.getContent());
        answer.setAccepted(answerEntity.getAccepted());
        return answer;
    }

    public static AnswerEntity toEntity(Answer answer){
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContent(answer.getContent());
        answerEntity.setAccepted(answer.getAccepted());
        return answerEntity;
    }

}
