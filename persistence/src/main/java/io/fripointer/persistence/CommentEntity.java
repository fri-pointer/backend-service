package io.fripointer.persistence;


import javax.persistence.*;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    @Column(name = "text")
    private String text;

    @Column(name = "parent_id")
    private String parentId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
