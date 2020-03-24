package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.StudentProgramEntity;
import io.fripointer.services.StudentProgramService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StudentProgramServiceImpl implements StudentProgramService {


    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<StudentProgramEntity> getStudentPrograms(QueryParameters params) {
        return JPAUtils.queryEntities(em, StudentProgramEntity.class, params);
    }

    public StudentProgramEntity getStudentProgram(String studentProgramId) {
        if(studentProgramId == null)
            return null;
        return em.find(StudentProgramEntity.class, studentProgramId);
    }

    @Transactional
    public StudentProgramEntity createStudentProgram(StudentProgramEntity studentProgram) {
        if(studentProgram == null)
            return null;
        em.persist(studentProgram);
        return studentProgram;
    }

    @Transactional
    public StudentProgramEntity updateStudentProgram(String studentProgramId, StudentProgramEntity studentProgram) {
        StudentProgramEntity sp = getStudentProgram(studentProgramId);
        if(sp == null || studentProgram == null)
            return null;
        studentProgram.setId(sp.getId());

        // TODO: DynamicUpdate

        em.merge(studentProgram);
        return studentProgram;
    }

    @Transactional
    public void removeStudentProgram(String studentProgramId) {
        StudentProgramEntity studentProgram = getStudentProgram(studentProgramId);
        if(studentProgram != null)
            em.remove(studentProgram);
    }
}
