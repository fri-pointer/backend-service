package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.SharedContent;
import io.fripointer.mappers.SharedContentMapper;
import io.fripointer.persistence.SharedContentEntity;
import io.fripointer.services.SharedContentService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SharedContentServiceImpl implements SharedContentService {

    private static final Logger log = LogManager.getLogger(SharedContentServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<SharedContent> getSharedContents(QueryParameters params) {
        List<SharedContent> sharedContents = JPAUtils.queryEntities(em, SharedContentEntity.class, params)
                .stream()
                .map(SharedContentMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, SharedContentEntity.class, params);
        return new EntityList<>(sharedContents, count);
    }

    @Override
    public SharedContent getSharedContent(String sharedContentId) {
        if(sharedContentId == null)
            return null;
        return SharedContentMapper.fromEntity(getSharedContentEntity(sharedContentId));
    }

    @Override
    public SharedContent createSharedContent(SharedContent sharedContent) {
        if(sharedContent == null) {
            log.info("SharedContent not created - input is null");
            return null;
        }

        SharedContentEntity sharedContentEntity = SharedContentMapper.toEntity(sharedContent);

        em.getTransaction().begin();
        em.persist(sharedContentEntity);
        em.getTransaction().commit();

        log.info("SharedContent with id {} created", sharedContent.getId());

        return SharedContentMapper.fromEntity(sharedContentEntity);
    }

    @Override
    public SharedContent updateSharedContent(String sharedContentId, SharedContent sharedContent) {
        if(sharedContent == null){
            log.info("SharedContent not created - input is null");
            return null;
        }

        SharedContentEntity sharedContentEntity = getSharedContentEntity(sharedContentId);
        if(sharedContentEntity == null) {
            log.info("SharedContent with id {} not found", sharedContentId);
            return null;
        }

        try {
            em.getTransaction().begin();
            sharedContentEntity.setTitle(sharedContent.getTitle());
            sharedContentEntity.setDescription(sharedContent.getDescription());
            em.getTransaction().commit();
            log.info("SharedContent with id {} updated successfully", sharedContentId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating SharedContent with id {}", sharedContentId);
        }
        return SharedContentMapper.fromEntity(sharedContentEntity);
    }

    @Override
    public void removeSharedContent(String sharedContentId) {
        SharedContentEntity sharedContentEntity = getSharedContentEntity(sharedContentId);
        if(sharedContentEntity != null) {
            em.getTransaction().begin();
            em.remove(sharedContentEntity);
            em.getTransaction().commit();
            log.info("SharedContent with id {} removed", sharedContentId);
        }
    }

    private SharedContentEntity getSharedContentEntity(String sharedContentId){
        if(sharedContentId == null)
            return null;
        return em.find(SharedContentEntity.class, sharedContentId);
    }
}
