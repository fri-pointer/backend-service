package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculties")
public class FacultyEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "abbrevation")
    private String abbreviation;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private UniversityEntity university;

    @OneToMany(mappedBy = "faculty")
    private List<StudentProgramEntity> studentPrograms;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UniversityEntity getUniversity() {
        return university;
    }

    public void setUniversity(UniversityEntity university) {
        this.university = university;
    }

    public List<StudentProgramEntity> getStudentPrograms() {
        return studentPrograms;
    }

    public void setStudentPrograms(List<StudentProgramEntity> studentPrograms) {
        this.studentPrograms = studentPrograms;
    }
}
