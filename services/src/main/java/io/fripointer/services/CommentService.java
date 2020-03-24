package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.CommentEntity;

import java.util.List;

public interface CommentService {
    List<CommentEntity> getComments(QueryParameters params);
    CommentEntity getComment(String commentId);
    CommentEntity createComment(CommentEntity comment);
    CommentEntity updateComment(String commentId, CommentEntity comment);
    void removeComment(String commentId);
}
