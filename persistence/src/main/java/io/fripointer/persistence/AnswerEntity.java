package io.fripointer.persistence;

import javax.persistence.*;

@Entity
@Table(name = "answers")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AnswerEntity extends CommentableEntity {
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "accepted")
    private Boolean accepted;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
    
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
    
    public QuestionEntity getQuestion() {
        return question;
    }
    
    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
