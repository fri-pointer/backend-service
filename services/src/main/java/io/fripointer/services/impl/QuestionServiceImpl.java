package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.QuestionEntity;
import io.fripointer.services.QuestionService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class QuestionServiceImpl implements QuestionService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<QuestionEntity> getQuestions(QueryParameters params) {
        return JPAUtils.queryEntities(em, QuestionEntity.class, params);
    }

    public QuestionEntity getQuestion(String questionId) {
        if(questionId == null)
            return null;
        return em.find(QuestionEntity.class, questionId);
    }

    @Transactional
    public QuestionEntity createQuestion(QuestionEntity question) {
        if(question == null)
            return null;

        em.persist(question);
        return question;
    }

    @Transactional
    public QuestionEntity updateQuestion(String questionId, QuestionEntity question) {
        QuestionEntity q = getQuestion(questionId);
        if(q == null || question == null)
            return null;

        question.setId(q.getId());

        // TODO: Dynamic update

        em.merge(question);
        return question;
    }

    @Transactional
    public void removeQuestion(String questionId) {
        QuestionEntity question = getQuestion(questionId);
        if(question != null)
            em.remove(question);
    }
}
