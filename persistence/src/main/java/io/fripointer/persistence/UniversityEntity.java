package io.fripointer.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "universities")
public class UniversityEntity extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="location")
    private String location;

    @OneToMany(mappedBy = "university")
    private List<FacultyEntity> faculties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<FacultyEntity> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<FacultyEntity> faculties) {
        this.faculties = faculties;
    }
}
