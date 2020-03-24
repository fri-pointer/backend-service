package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.CommentEntity;
import io.fripointer.services.CommentService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<CommentEntity> getComments(QueryParameters params) {
        return JPAUtils.queryEntities(em, CommentEntity.class, params);
    }

    public CommentEntity getComment(String commentId) {
        if(commentId == null)
            return null;
        return em.find(CommentEntity.class, commentId);
    }

    @Transactional
    public CommentEntity createComment(CommentEntity comment) {
        if (comment == null)
            return null;
        em.persist(comment);
        return comment;
    }


    @Transactional
    public CommentEntity updateComment(String commentId, CommentEntity comment) {
        CommentEntity c = getComment(commentId);
        if (c == null || comment == null)
            return null;

        // TODO: Set id
        // TODO: Dynamic update --> if needed, because comment contains only 1 field (text)

        em.merge(comment);
        return comment;
    }

    @Transactional
    public void removeComment(String commentId) {
        CommentEntity comment = getComment(commentId);
        if(comment != null)
            em.remove(comment);
    }
}
