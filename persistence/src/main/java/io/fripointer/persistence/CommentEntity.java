package io.fripointer.persistence;


import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = CommentEntity.FIND_BY_PARENT_ID, query = "SELECT c FROM CommentEntity c WHERE c.parentId = :parentId")
})
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    public static final String FIND_BY_PARENT_ID = "CommentEntity.findByParentId";

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "text")
    private String text;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
