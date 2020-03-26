package io.fripointer.mappers;

import io.fripointer.lib.Faculty;
import io.fripointer.persistence.FacultyEntity;

public class FacultyMapper {

    public static Faculty fromEntity(FacultyEntity facultyEntity){
        Faculty faculty = new Faculty();
        faculty.setId(facultyEntity.getId());
        faculty.setTimestamp(facultyEntity.getTimestamp());
        faculty.setName(facultyEntity.getName());
        faculty.setAbbreviation(facultyEntity.getAbbreviation());
        faculty.setDescription(facultyEntity.getDescription());
        faculty.setLocation(facultyEntity.getLocation());
        return faculty;
    }

    public static FacultyEntity toEntity(Faculty faculty){
        FacultyEntity facultyEntity = new FacultyEntity();
        facultyEntity.setName(faculty.getName());
        facultyEntity.setAbbreviation(faculty.getAbbreviation());
        facultyEntity.setDescription(faculty.getDescription());
        facultyEntity.setLocation(faculty.getLocation());
        return facultyEntity;
    }
}
