package io.fripointer.persistence;


import javax.persistence.*;

@Entity
@NamedQueries({
    // @NamedQuery(name = CommentEntity.FIND_BY_PARENT_ID, query = "SELECT c FROM CommentEntity c WHERE c.parentId = :parentId"),
    // @NamedQuery(name = CommentEntity.FIND_BY_PARENT_ID_AND_COMMENT_ID, query = "SELECT c FROM CommentEntity c WHERE c.parentId = :parentId AND c.id = :commentId")
})
@Table(name = "comments")
public class CommentEntity extends BaseEntity {
    
    public static final String FIND_BY_PARENT_ID = "CommentEntity.findByParentId";
    public static final String FIND_BY_PARENT_ID_AND_COMMENT_ID = "CommentEntity.findByParentIdAndCommentId";
    
    @Column(name = "text")
    private String text;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentableEntity parent;
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public CommentableEntity getParent() {
        return parent;
    }
    
    public void setParent(CommentableEntity parent) {
        this.parent = parent;
    }
}
