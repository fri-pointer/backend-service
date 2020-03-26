package io.fripointer.mappers;

import io.fripointer.lib.Comment;
import io.fripointer.persistence.CommentEntity;

public class CommentMapper {

    public static Comment fromEntity(CommentEntity commentEntity){
        Comment comment = new Comment();
        comment.setId(commentEntity.getId());
        comment.setTimestamp(commentEntity.getTimestamp());
        comment.setText(commentEntity.getText());
        return comment;
    }

    public static CommentEntity toEntity(Comment comment){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setText(comment.getText());
        return commentEntity;
    }

}
