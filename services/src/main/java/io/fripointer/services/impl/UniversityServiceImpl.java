package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.lib.University;
import io.fripointer.mappers.UniversityMapper;
import io.fripointer.persistence.UniversityEntity;
import io.fripointer.services.UniversityService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UniversityServiceImpl implements UniversityService {
    
    private static final Logger log = LogManager.getLogger(UniversityServiceImpl.class.getName());
    
    // TODO: Custom exceptions (Not modified, entity required etc.)
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Override
    public EntityList<University> getUniversities(QueryParameters params) {
        List<University> universities = JPAUtils.queryEntities(em, UniversityEntity.class, params)
            .stream()
            .map(UniversityMapper::fromEntity)
            .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, UniversityEntity.class, params);
        return new EntityList<>(universities, count);
    }
    
    @Override
    public University getUniversity(String universityId) {
        UniversityEntity entity = getUniversityEntity(universityId);
        if (entity == null) {
            throw new NotFoundException("university.error.not-found");
        }
        return UniversityMapper.fromEntity(entity);
    }
    
    @Override
    public University createUniversity(University university) {
        if (university == null) {
            log.info("University not created - input is null");
            return null;
        }
        
        UniversityEntity universityEntity = UniversityMapper.toEntity(university);
        
        em.getTransaction().begin();
        em.persist(universityEntity);
        em.getTransaction().commit();
        
        log.info("University with id {} created", university.getId());
        
        return UniversityMapper.fromEntity(universityEntity);
    }
    
    @Override
    public University updateUniversity(String universityId, University university) {
        if (university == null) {
            log.info("University not created - input is null");
            return null;
        }
        
        UniversityEntity universityEntity = getUniversityEntity(universityId);
        if (universityEntity == null) {
            log.info("University with id {} not found", universityId);
            return null;
        }
        
        try {
            em.getTransaction().begin();
            universityEntity.setName(university.getName());
            universityEntity.setLocation(university.getLocation());
            em.getTransaction().commit();
            log.info("University with id {} updated successfully", universityId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating University with id {}", universityId);
        }
        return UniversityMapper.fromEntity(universityEntity);
    }
    
    @Override
    public void removeUniversity(String universityId) {
        UniversityEntity universityEntity = getUniversityEntity(universityId);
        if (universityEntity != null) {
            em.getTransaction().begin();
            em.remove(universityEntity);
            em.getTransaction().commit();
            log.info("University with id {} removed", universityId);
        }
    }
    
    private UniversityEntity getUniversityEntity(String universityId) {
        if (universityId == null)
            return null;
        return em.find(UniversityEntity.class, universityId);
    }
    
}
