package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.FacultyEntity;

import java.util.List;

public interface FacultyService {

    List<FacultyEntity> getFaculties(QueryParameters params);
    FacultyEntity getFaculty(String facultyId);
    FacultyEntity createFaculty(FacultyEntity faculty);
    FacultyEntity updateFaculty(String facultyId, FacultyEntity faculty);
    void removeFaculty(String facultyId);
    
}
