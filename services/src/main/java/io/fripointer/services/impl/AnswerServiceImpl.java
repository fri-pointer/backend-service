package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.Queried;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Answer;
import io.fripointer.mappers.AnswerMapper;
import io.fripointer.persistence.AnswerEntity;
import io.fripointer.services.AnswerService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnswerServiceImpl implements AnswerService {

    private static final Logger log = LogManager.getLogger(AnswerServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<Answer> getAnswers(QueryParameters params) {
        Queried<AnswerEntity> queried = JPAUtils.getQueried(em, AnswerEntity.class, params);
        
        List<Answer> answers = queried.stream()
                .map(AnswerMapper::fromEntity)
                .collect(Collectors.toList());
        
        return new EntityList<>(answers, queried.getTotalCount());
    }

    @Override
    public Answer getAnswer(String answerId) {
        if(answerId == null)
            return null;
        return AnswerMapper.fromEntity(getAnswerEntity(answerId));
    }

    @Override
    public Answer createAnswer(Answer answer) {

        if(answer == null) {
            log.info("Answer not created - input is null");
            return null;
        }

        AnswerEntity answerEntity = AnswerMapper.toEntity(answer);

        em.getTransaction().begin();
        em.persist(answerEntity);
        em.getTransaction().commit();

        log.info("Answer with id {} created", answerEntity.getId());

        return AnswerMapper.fromEntity(answerEntity);
    }

    @Override
    public Answer updateAnswer(String answerId, Answer answer) {

        if(answer == null){
            log.info("Answer not created - input is null");
            return null;
        }

        AnswerEntity answerEntity = getAnswerEntity(answerId);

        if(answerEntity == null) {
            log.info("Answer with id {} not found", answerId);
            return null;
        }

        try {
            em.getTransaction().begin();
            answerEntity.setContent(answer.getContent());
            answerEntity.setAccepted(answer.getAccepted());
            em.getTransaction().commit();
            log.info("Answer with id {} updated successfully", answerId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating Answer with id {}", answerId);
        }
        return answer;
    }

    @Override
    public void removeAnswer(String answerId) {
        AnswerEntity answerEntity = getAnswerEntity(answerId);
        if(answerEntity != null) {
            em.getTransaction().begin();
            em.remove(answerEntity);
            em.getTransaction().commit();
            log.info("Answer with id {} removed", answerId);
        }
    }

    private AnswerEntity getAnswerEntity(String answerId){
        if(answerId == null)
            return null;
        return em.find(AnswerEntity.class, answerId);
    }
}
