package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;
import io.fripointer.mappers.CommentMapper;
import io.fripointer.persistence.CommentEntity;
import io.fripointer.services.CommentService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LogManager.getLogger(CommentServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<Comment> getCommentsByParentId(String parentId, QueryParameters params) {
        TypedQuery<CommentEntity> query = em.createNamedQuery(CommentEntity.FIND_BY_PARENT_ID, CommentEntity.class);
        query.setParameter("parentId", parentId);

        List<Comment> comments = query.getResultStream()
                .map(CommentMapper::fromEntity)
                .collect(Collectors.toList());

        long count = comments.size();

        return new EntityList<>(comments, count);
    }

    @Override
    public EntityList<Comment> getComments(QueryParameters params) {
        List<Comment> comments = JPAUtils.queryEntities(em, CommentEntity.class, params)
                .stream()
                .map(CommentMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, CommentEntity.class, params);
        return new EntityList<>(comments, count);
    }

    @Override
    public Comment getComment(String parentId, String commentId) {
        if(commentId == null || parentId == null)
            return null;
        return CommentMapper.fromEntity(getCommentEntityByParentId(parentId, commentId));
    }

    @Override
    public Comment createComment(String parentId, Comment comment) {

        if(comment == null) {
            log.info("Comment not created - input is null");
            return null;
        }

        // TODO: Handle incorrect parent_id
        CommentEntity commentEntity = CommentMapper.toEntity(comment);
        // commentEntity.setParentId(parentId);

        em.getTransaction().begin();
        em.persist(commentEntity);
        em.getTransaction().commit();

        log.info("Comment with id {} created", commentEntity.getId());

        return CommentMapper.fromEntity(commentEntity);
    }


    @Override
    public Comment updateComment(String parentId, String commentId, Comment comment) {
        if(comment == null){
            log.info("Comment not created - input is null");
            return null;
        }

        CommentEntity commentEntity = getCommentEntityByParentId(parentId, commentId);

        if(commentEntity == null) {
            log.info("Comment with id {} not found", commentId);
            return null;
        }

        try {
            em.getTransaction().begin();
            // Parent id must not be changed
            commentEntity.setText(comment.getText());
            em.getTransaction().commit();
            log.info("Comment with id {} updated successfully", commentId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating Comment with id {}", commentId);
        }
        return comment;
    }

    @Override
    public void removeComment(String parentId, String commentId) {
        CommentEntity commentEntity = getCommentEntityByParentId(parentId, commentId);
        if(commentEntity != null) {
            em.getTransaction().begin();
            em.remove(commentEntity);
            em.getTransaction().commit();
            log.info("Comment with id {} removed", commentId);
        }
    }

    public CommentEntity getCommentEntityByParentId(String parentId, String commentId) {
        if(commentId == null || parentId == null)
            return null;
        return (CommentEntity) em.createNamedQuery(CommentEntity.FIND_BY_PARENT_ID_AND_COMMENT_ID)
                .setParameter("parentId", parentId)
                .setParameter("commentId", commentId).getSingleResult();
    }
}
