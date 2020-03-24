package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="student_program")
public class StudentProgramEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "abbrevation")
    private String abbrevation;

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

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
