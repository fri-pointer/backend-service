package io.fripointer.mappers;

import io.fripointer.lib.University;
import io.fripointer.persistence.UniversityEntity;

public class UniversityMapper {

    public static University fromEntity(UniversityEntity universityEntity){
        University university = new University();
        university.setId(universityEntity.getId());
        university.setTimestamp(universityEntity.getTimestamp());
        university.setName(universityEntity.getName());
        university.setLocation(universityEntity.getLocation());
        return university;
    }

    public static UniversityEntity toEntity(University university) {
        UniversityEntity universityEntity = new UniversityEntity();
        universityEntity.setName(university.getName());
        universityEntity.setLocation(university.getLocation());
        return universityEntity;
    }

}
