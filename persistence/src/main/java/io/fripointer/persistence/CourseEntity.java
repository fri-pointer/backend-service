package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="course")
public class CourseEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "abbrevation")
    private String abbrevation;

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

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
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
}
