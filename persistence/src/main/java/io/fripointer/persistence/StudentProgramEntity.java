package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="student_programs")
public class StudentProgramEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "abbrevation")
    private String abbreviation;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private FacultyEntity faculty;

    @OneToMany(mappedBy = "studentProgram")
    private List<CourseEntity> courses;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FacultyEntity getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyEntity faculty) {
        this.faculty = faculty;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }
}
