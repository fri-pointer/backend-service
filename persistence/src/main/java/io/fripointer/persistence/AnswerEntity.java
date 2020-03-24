package io.fripointer.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class AnswerEntity extends BaseEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "accepted")
    private Boolean accepted;

    // TODO: FK -> question

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
