package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.FacultyEntity;
import io.fripointer.services.FacultyService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FacultyServiceImpl implements FacultyService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<FacultyEntity> getFaculties(QueryParameters params) {
        return JPAUtils.queryEntities(em, FacultyEntity.class, params);
    }

    public FacultyEntity getFaculty(String facultyId) {
        if(facultyId == null)
            return null;
        return em.find(FacultyEntity.class, facultyId);
    }

    @Transactional
    public FacultyEntity createFaculty(FacultyEntity faculty) {
        if(faculty == null)
            return null;

        em.persist(faculty);

        return faculty;
    }

    @Transactional
    public FacultyEntity updateFaculty(String facultyId, FacultyEntity faculty) {
        FacultyEntity f = getFaculty(facultyId);
        if(f == null || faculty == null)
            return null;

        faculty.setId(f.getId());

        // TODO: DynamicUpdate

        em.merge(faculty);
        return faculty;
    }

    @Transactional
    public void removeFaculty(String facultyId) {
        FacultyEntity faculty = getFaculty(facultyId);
        if(faculty != null)
            em.remove(faculty);
    }
}
