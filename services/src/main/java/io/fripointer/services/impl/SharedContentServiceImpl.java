package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.SharedContentEntity;
import io.fripointer.services.SharedContentService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SharedContentServiceImpl implements SharedContentService {
    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<SharedContentEntity> getSharedContents(QueryParameters params) {
        return JPAUtils.queryEntities(em, SharedContentEntity.class, params);
    }

    public SharedContentEntity getSharedContent(String sharedContentId) {
        if(sharedContentId == null)
            return null;
        return em.find(SharedContentEntity.class, sharedContentId);
    }

    @Transactional
    public SharedContentEntity createSharedContent(SharedContentEntity sharedContent) {
        if(sharedContent == null)
            return null;

        em.persist(sharedContent);
        return sharedContent;
    }

    @Transactional
    public SharedContentEntity updateSharedContent(String sharedContentId, SharedContentEntity sharedContent) {
        SharedContentEntity sc = getSharedContent(sharedContentId);
        if(sc == null || sharedContent == null)
            return null;

        sharedContent.setId(sc.getId());

        // TODO: Dynamic update

        em.merge(sharedContent);

        return sharedContent;
    }

    @Transactional
    public void removeSharedContent(String sharedContentId) {
        SharedContentEntity sharedContent = getSharedContent(sharedContentId);
        if(sharedContent != null)
            em.remove(sharedContent);
    }
}
