package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Question;
import io.fripointer.mappers.QuestionMapper;
import io.fripointer.persistence.QuestionEntity;
import io.fripointer.services.QuestionService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionServiceImpl implements QuestionService {

    private static final Logger log = LogManager.getLogger(QuestionServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<Question> getQuestions(QueryParameters params) {
        List<Question> questions = JPAUtils.queryEntities(em, QuestionEntity.class, params)
                .stream()
                .map(QuestionMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, QuestionEntity.class, params);
        return new EntityList<>(questions, count);
    }

    @Override
    public Question getQuestion(String questionId) {
        QuestionEntity questionEntity = getQuestionEntity(questionId);
        return QuestionMapper.fromEntity(questionEntity);
    }

    @Override
    public Question createQuestion(Question question) {

        if(question == null) {
            log.info("Question not created - input is null");
            return null;
        }

        QuestionEntity questionEntity = QuestionMapper.toEntity(question);

        em.getTransaction().begin();
        em.persist(questionEntity);
        em.getTransaction().commit();

        log.info("Question with id {} created", questionEntity.getId());

        return QuestionMapper.fromEntity(questionEntity);
    }

    @Override
    public Question updateQuestion(String questionId, Question question) {

        if(question == null){
            log.info("Question not created - input is null");
            return null;
        }

        QuestionEntity questionEntity = getQuestionEntity(questionId);
        if(questionEntity == null) {
            log.info("Question with id {} not found", questionId);
            return null;
        }

        try {
            em.getTransaction().begin();
            questionEntity.setTitle(question.getTitle());
            questionEntity.setContent(question.getContent());
            em.getTransaction().commit();
            log.info("Question with id {} updated successfully", questionId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating Question with id {}", questionId);
        }
        return question;
    }

    @Override
    public void removeQuestion(String questionId) {
        QuestionEntity questionEntity = getQuestionEntity(questionId);
        if(questionEntity != null) {
            em.getTransaction().begin();
            em.remove(questionEntity);
            em.getTransaction().commit();
            log.info("Question with id {} removed", questionId);
        }
    }

    private QuestionEntity getQuestionEntity(String questionId){
        if(questionId == null)
            return null;
        return em.find(QuestionEntity.class, questionId);
    }
}
