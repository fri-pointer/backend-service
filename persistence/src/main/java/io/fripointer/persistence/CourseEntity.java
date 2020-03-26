package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="courses")
public class CourseEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "abbrevation")
    private String abbreviation;

    @Column(name = "year")
    private Integer year;

    @Column(name = "owner")
    private String owner;

    @ManyToOne
    @JoinColumn(name = "student_program_id")
    private StudentProgramEntity studentProgram;

    @OneToMany(mappedBy = "course")
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "course")
    private List<QuestionEntity> questions;

    @OneToMany(mappedBy = "course")
    private List<SharedContentEntity> sharedContents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public StudentProgramEntity getStudentProgram() {
        return studentProgram;
    }

    public void setStudentProgram(StudentProgramEntity studentProgram) {
        this.studentProgram = studentProgram;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public List<SharedContentEntity> getSharedContents() {
        return sharedContents;
    }

    public void setSharedContents(List<SharedContentEntity> sharedContents) {
        this.sharedContents = sharedContents;
    }
}
