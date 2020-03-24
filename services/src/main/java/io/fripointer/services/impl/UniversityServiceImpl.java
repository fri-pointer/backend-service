package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.UniversityEntity;
import io.fripointer.services.UniversityService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UniversityServiceImpl implements UniversityService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<UniversityEntity> getUniversities(QueryParameters params) {
        return JPAUtils.queryEntities(em, UniversityEntity.class, params);
    }

    public UniversityEntity getUniversity(String universityId) {
        if(universityId == null)
            return null;
        return em.find(UniversityEntity.class, universityId);
    }

    @Transactional
    public UniversityEntity createUniversity(UniversityEntity university) {
        if(university == null)
            return null;
        em.persist(university);
        return university;
    }

    @Transactional
    public UniversityEntity updateUniversity(String universityId, UniversityEntity university) {
        UniversityEntity uni = getUniversity(universityId);

        if(uni == null || university == null)
            return null;

        university.setId(uni.getId());

        // TODO: DynamicUpdate

        em.merge(university);
        return university;
    }

    @Transactional
    public void removeUniversity(String universityId) {
        UniversityEntity university = getUniversity(universityId);
        if(university != null){
            em.remove(university);
        }
    }
}
