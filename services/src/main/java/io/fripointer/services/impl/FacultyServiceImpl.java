package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Faculty;
import io.fripointer.mappers.FacultyMapper;
import io.fripointer.persistence.FacultyEntity;
import io.fripointer.services.FacultyService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FacultyServiceImpl implements FacultyService {

    private static final Logger log = LogManager.getLogger(FacultyServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<Faculty> getFaculties(QueryParameters params) {
        List<Faculty> faculties = JPAUtils.queryEntities(em, FacultyEntity.class, params)
                .stream()
                .map(FacultyMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, FacultyEntity.class, params);
        return new EntityList<>(faculties, count);
    }

    @Override
    public Faculty getFaculty(String facultyId) {
        if(facultyId == null)
            return null;
        return FacultyMapper.fromEntity(getFacultyEntity(facultyId));
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {

        if(faculty == null) {
            log.info("Faculty not created - input is null");
            return null;
        }

        FacultyEntity facultyEntity = FacultyMapper.toEntity(faculty);

        em.getTransaction().begin();
        em.persist(facultyEntity);
        em.getTransaction().commit();

        log.info("Faculty with id {} created", facultyEntity.getId());

        return FacultyMapper.fromEntity(facultyEntity);
    }

    @Override
    public Faculty updateFaculty(String facultyId, Faculty faculty) {
        if(faculty == null){
            log.info("Faculty not created - input is null");
            return null;
        }

        FacultyEntity facultyEntity = getFacultyEntity(facultyId);
        if(facultyEntity == null) {
            log.info("Faculty with id {} not found", facultyId);
            return null;
        }

        try {
            em.getTransaction().begin();
            facultyEntity.setName(faculty.getName());
            facultyEntity.setAbbreviation(faculty.getAbbreviation());
            facultyEntity.setDescription(faculty.getDescription());
            facultyEntity.setLocation(faculty.getLocation());
            em.getTransaction().commit();
            log.info("Faculty with id {} updated successfully", facultyId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating Faculty with id {}", facultyId);
        }
        return FacultyMapper.fromEntity(facultyEntity);
    }

    @Override
    public void removeFaculty(String facultyId) {
        FacultyEntity facultyEntity = getFacultyEntity(facultyId);
        if(facultyEntity != null) {
            em.getTransaction().begin();
            em.remove(facultyEntity);
            em.getTransaction().commit();
            log.info("Faculty with id {} removed", facultyId);
        }

    }

    private FacultyEntity getFacultyEntity(String facultyId){
        if(facultyId == null)
            return null;
        return em.find(FacultyEntity.class, facultyId);
    }
}
