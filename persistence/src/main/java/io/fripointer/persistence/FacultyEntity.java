package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
public class FacultyEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "abbrevation")
    private String abbrevation;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
