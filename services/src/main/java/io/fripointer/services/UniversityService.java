package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.UniversityEntity;

import java.util.List;

public interface UniversityService {
    List<UniversityEntity> getUniversities(QueryParameters params);
    UniversityEntity getUniversity(String universityId);
    UniversityEntity createUniversity(UniversityEntity university);
    UniversityEntity updateUniversity(String universityId, UniversityEntity university);
    void removeUniversity(String universityId);
}
