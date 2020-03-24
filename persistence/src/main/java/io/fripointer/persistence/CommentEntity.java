package io.fripointer.persistence;


import javax.persistence.*;

@Entity
@Table(name = "comment")
public class CommentEntity {

    @Column(name = "text")
    private String text;

    // TODO: Relations to post, answer and question

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
