package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;

public interface CommentService {
    EntityList<Comment> getComments(QueryParameters params);
    Comment getComment(String commentId);
    Comment createComment(Comment comment);
    Comment updateComment(String commentId, Comment comment);
    void removeComment(String commentId);
}
