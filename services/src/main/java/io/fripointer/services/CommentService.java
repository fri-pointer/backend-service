package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;

public interface CommentService {
    EntityList<Comment> getComments(QueryParameters params);
    EntityList<Comment> getCommentsByParentId(String parentId, QueryParameters params);
    Comment getComment(String parentId, String commentId);
    Comment createComment(String parentId, Comment comment);
    Comment updateComment(String parentId, String commentId, Comment comment);
    void removeComment(String parentId, String commentId);
}
