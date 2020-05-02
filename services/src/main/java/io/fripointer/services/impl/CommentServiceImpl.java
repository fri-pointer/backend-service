package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.lib.Comment;
import io.fripointer.mappers.CommentMapper;
import io.fripointer.persistence.CommentEntity;
import io.fripointer.persistence.CommentableEntity;
import io.fripointer.persistence.constants.PersistenceConst;
import io.fripointer.services.CommentService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LogManager.getLogger(CommentServiceImpl.class.getName());

    @PersistenceContext(unitName = PersistenceConst.MAIN_JPA_UNIT)
    private EntityManager em;

    @Override
    public EntityList<Comment> getCommentsByParentId(String parentId, QueryParameters params) {
        params.getFilters().add(new QueryFilter("parentId", FilterOperation.EQ, parentId));
        return getComments(params);
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
    public Comment getComment(String commentId) {
        CommentEntity entity = em.find(CommentEntity.class, commentId);
        if(entity == null) {
            throw new NotFoundException("error.not-found", CommentEntity.class, commentId);
        }
        return CommentMapper.fromEntity(entity);
    }

    @Override
    public Comment createComment(String parentId, Comment comment) {

        if(comment == null) {
            log.info("Comment not created - input is null");
            return null;
        }
    
        CommentableEntity parent = em.find(CommentableEntity.class, parentId);
        if (parent == null) {
            throw new NotFoundException("error.not-found", CommentableEntity.class, parentId);
        }
        
        CommentEntity newComment = new CommentEntity();
        newComment.setParent(parent);
        newComment.setText(comment.getText());
        
        em.getTransaction().begin();
        em.persist(newComment);
        em.getTransaction().commit();

        log.info("Comment with id {} created", newComment.getId());

        return CommentMapper.fromEntity(newComment);
    }


    @Override
    public Comment updateComment(String commentId, Comment comment) {
        if(comment == null){
            log.info("Comment not created - input is null");
            return null;
        }

        CommentEntity commentEntity = em.find(CommentEntity.class, commentId);
        if(commentEntity == null) {
            throw new NotFoundException("error.not-found", CommentEntity.class, commentId);
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
    public void removeComment(String commentId) {
        CommentEntity commentEntity = em.find(CommentEntity.class, commentId);
        
        if (commentEntity == null) {
            throw new NotFoundException("error.not-found", CommentEntity.class, commentId);
        }
        
        em.getTransaction().begin();
        em.remove(commentEntity);
        em.getTransaction().commit();
        log.info("Comment with id {} removed", commentId);
    }

}
