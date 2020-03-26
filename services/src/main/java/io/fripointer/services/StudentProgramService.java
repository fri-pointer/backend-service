package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.StudentProgram;

public interface StudentProgramService {
    EntityList<StudentProgram> getStudentPrograms(QueryParameters params);
    StudentProgram getStudentProgram(String studentProgramId);
    StudentProgram createStudentProgram(StudentProgram studentProgram);
    StudentProgram updateStudentProgram(String studentProgramId, StudentProgram studentProgram);
    void removeStudentProgram(String studentProgramId);
    
}
