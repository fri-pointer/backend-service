package io.fripointer.mappers;

import io.fripointer.lib.Question;
import io.fripointer.persistence.QuestionEntity;

public class QuestionMapper {

    public static Question fromEntity(QuestionEntity questionEntity){
        Question question = new Question();
        question.setId(questionEntity.getId());
        question.setTimestamp(questionEntity.getTimestamp());
        question.setTitle(questionEntity.getTitle());
        question.setContent(questionEntity.getContent());
        return question;
    }

    public static QuestionEntity toEntity(Question question){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(question.getTitle());
        questionEntity.setContent(question.getContent());
        return questionEntity;
    }

}
