package io.fripointer.persistence;


import javax.persistence.*;

@Entity
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PostEntity extends CommentableEntity {
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    
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
}
