package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.StudentProgramEntity;

import java.util.List;

public interface StudentProgramService {
    List<StudentProgramEntity> getStudentPrograms(QueryParameters params);
    StudentProgramEntity getStudentProgram(String studentProgramId);
    StudentProgramEntity createStudentProgram(StudentProgramEntity studentProgram);
    StudentProgramEntity updateStudentProgram(String studentProgramId, StudentProgramEntity studentProgram);
    void removeStudentProgram(String studentProgramId);
    
}
