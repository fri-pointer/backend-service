package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.University;

public interface UniversityService {
    EntityList<University> getUniversities(QueryParameters params);
    University getUniversity(String universityId);
    University createUniversity(University university);
    University updateUniversity(String universityId, University university);
    void removeUniversity(String universityId);
}
