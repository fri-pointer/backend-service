package io.fripointer.mappers;

import io.fripointer.lib.Question;
import io.fripointer.persistence.QuestionEntity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static Question fromEntity(QuestionEntity questionEntity){
        Question question = new Question();
        question.setId(questionEntity.getId());
        question.setTimestamp(questionEntity.getTimestamp());
        question.setTitle(questionEntity.getTitle());
        question.setContent(questionEntity.getContent());
        return question;
    }

    public static Question fromEntityDetailed(QuestionEntity questionEntity){
        Question question = fromEntity(questionEntity);

        if(questionEntity.getAnswers() != null) {
            question.setAnswers(questionEntity.getAnswers()
                    .stream()
                    .map(AnswerMapper::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            question.setAnswers(new ArrayList<>());
        }

        return question;
    }

    public static QuestionEntity toEntity(Question question){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(question.getTitle());
        questionEntity.setContent(question.getContent());
        return questionEntity;
    }

}
