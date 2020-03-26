package io.fripointer.mappers;

import io.fripointer.lib.StudentProgram;
import io.fripointer.persistence.StudentProgramEntity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StudentProgramMapper {

    public static StudentProgram fromEntity(StudentProgramEntity studentProgramEntity) {
        StudentProgram studentProgram = new StudentProgram();
        studentProgram.setId(studentProgramEntity.getId());
        studentProgram.setTimestamp(studentProgramEntity.getTimestamp());
        studentProgram.setName(studentProgramEntity.getName());
        studentProgram.setDescription(studentProgramEntity.getDescription());
        studentProgram.setAbbreviation(studentProgramEntity.getAbbreviation());
        return studentProgram;
    }

    public static StudentProgram fromEntityDetailed(StudentProgramEntity studentProgramEntity) {
        StudentProgram studentProgram = fromEntity(studentProgramEntity);

        if(studentProgramEntity.getCourses() != null) {
            studentProgram.setCourses(studentProgramEntity.getCourses()
                    .stream()
                    .map(CourseMapper::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            studentProgram.setCourses(new ArrayList<>());
        }

        return studentProgram;
    }

    public static StudentProgramEntity toEntity(StudentProgram studentProgram) {
        StudentProgramEntity studentProgramEntity = new StudentProgramEntity();
        studentProgramEntity.setName(studentProgram.getName());
        studentProgramEntity.setDescription(studentProgram.getDescription());
        studentProgramEntity.setAbbreviation(studentProgram.getAbbreviation());
        return studentProgramEntity;
    }

}
