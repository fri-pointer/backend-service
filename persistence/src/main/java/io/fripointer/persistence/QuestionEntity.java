package io.fripointer.persistence;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class QuestionEntity extends CommentableEntity {
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    
    @OneToMany(mappedBy = "question")
    private List<AnswerEntity> answers;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public CourseEntity getCourse() {
        return course;
    }
    
    public void setCourse(CourseEntity course) {
        this.course = course;
    }
    
    public List<AnswerEntity> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }
}
