package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Faculty;
import io.fripointer.persistence.FacultyEntity;

import java.util.List;

public interface FacultyService {

    EntityList<Faculty> getFaculties(QueryParameters params);
    Faculty getFaculty(String facultyId);
    Faculty createFaculty(Faculty faculty);
    Faculty updateFaculty(String facultyId, Faculty faculty);
    void removeFaculty(String facultyId);
    
}
