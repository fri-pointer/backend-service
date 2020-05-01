package io.fripointer.mappers;

import io.fripointer.lib.Comment;
import io.fripointer.persistence.CommentEntity;

public class CommentMapper {

    public static Comment fromEntity(CommentEntity commentEntity){
        Comment comment = new Comment();
        comment.setId(commentEntity.getId());
        // comment.setParentId(commentEntity.getParentId());
        comment.setTimestamp(commentEntity.getTimestamp());
        comment.setText(commentEntity.getText());
        return comment;
    }

    public static CommentEntity toEntity(Comment comment){
        CommentEntity commentEntity = new CommentEntity();
        // TODO: Handle possibility of parent_id violation
        // commentEntity.setParentId(comment.getId());
        commentEntity.setText(comment.getText());
        return commentEntity;
    }

}
