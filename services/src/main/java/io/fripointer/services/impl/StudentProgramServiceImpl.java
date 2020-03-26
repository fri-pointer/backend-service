package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.StudentProgram;
import io.fripointer.mappers.StudentProgramMapper;
import io.fripointer.persistence.StudentProgramEntity;
import io.fripointer.services.StudentProgramService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentProgramServiceImpl implements StudentProgramService {


    private static final Logger log = LogManager.getLogger(StudentProgramServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<StudentProgram> getStudentPrograms(QueryParameters params) {
        List<StudentProgram> studentPrograms = JPAUtils.queryEntities(em, StudentProgramEntity.class, params)
                .stream()
                .map(StudentProgramMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, StudentProgramEntity.class, params);
        return new EntityList<>(studentPrograms, count);
    }

    @Override
    public StudentProgram getStudentProgram(String studentProgramId) {
        if(studentProgramId == null)
            return null;
        return StudentProgramMapper.fromEntity(getStudentProgramEntity(studentProgramId));
    }

    @Override
    public StudentProgram createStudentProgram(StudentProgram studentProgram) {
        if(studentProgram == null) {
            log.info("StudentProgram not created - input is null");
            return null;
        }

        StudentProgramEntity studentProgramEntity = StudentProgramMapper.toEntity(studentProgram);

        em.getTransaction().begin();
        em.persist(studentProgramEntity);
        em.getTransaction().commit();

        log.info("StudentProgram with id {} created", studentProgramEntity.getId());

        return StudentProgramMapper.fromEntity(studentProgramEntity);
    }

    @Override
    public StudentProgram updateStudentProgram(String studentProgramId, StudentProgram studentProgram) {
        if(studentProgram == null){
            log.info("StudentProgram not created - input is null");
            return null;
        }

        StudentProgramEntity studentProgramEntity = getStudentProgramEntity(studentProgramId);
        if(studentProgramEntity == null) {
            log.info("StudentProgram with id {} not found", studentProgramId);
            return null;
        }

        try {
            em.getTransaction().begin();
            studentProgramEntity.setName(studentProgram.getName());
            studentProgramEntity.setDescription(studentProgram.getDescription());
            studentProgramEntity.setAbbreviation(studentProgram.getAbbreviation());
            em.getTransaction().commit();
            log.info("StudentProgram with id {} updated successfully", studentProgramId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating StudentProgram with id {}", studentProgramId);
        }
        return StudentProgramMapper.fromEntity(studentProgramEntity);
    }

    @Override
    public void removeStudentProgram(String studentProgramId) {
        StudentProgramEntity studentProgramEntity = getStudentProgramEntity(studentProgramId);
        if(studentProgramEntity != null) {
            em.getTransaction().begin();
            em.remove(studentProgramEntity);
            em.getTransaction().commit();
            log.info("StudentProgram with id {} removed", studentProgramId);
        }
    }

    private StudentProgramEntity getStudentProgramEntity(String studentProgramId){
        if(studentProgramId == null)
            return null;
        return em.find(StudentProgramEntity.class, studentProgramId);
    }
}
