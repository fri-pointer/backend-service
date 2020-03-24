package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.AnswerEntity;
import io.fripointer.services.AnswerService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AnswerServiceImpl implements AnswerService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<AnswerEntity> getAnswers(QueryParameters params) {
        return JPAUtils.queryEntities(em, AnswerEntity.class, params);
    }

    public AnswerEntity getAnswer(String answerId) {
        if(answerId == null)
            return null;
        return em.find(AnswerEntity.class, answerId);
    }

    @Transactional
    public AnswerEntity createAnswer(AnswerEntity answer) {
        if(answer == null)
            return null;
        em.persist(answer);
        return answer;
    }

    @Transactional
    public AnswerEntity updateAnswer(String answerId, AnswerEntity answer) {
        AnswerEntity a = getAnswer(answerId);
        if(a == null || answer == null)
            return null;

        answer.setId(a.getId());

        // TODO: Dynamic update

        em.merge(answer);
        return answer;
    }

    @Transactional
    public void removeAnswer(String answerId) {
        AnswerEntity answer = getAnswer(answerId);
        if(answer != null)
            em.remove(answer);
    }
}
