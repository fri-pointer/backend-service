package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.FileEntity;
import io.fripointer.services.FileService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<FileEntity> getFiles(QueryParameters params) {
        return JPAUtils.queryEntities(em, FileEntity.class, params);
    }

    public FileEntity getFile(String fileId) {
        if(fileId == null)
            return null;
        return em.find(FileEntity.class, fileId);
    }

    @Transactional
    public FileEntity createFile(FileEntity file) {
        if(file == null)
            return null;
        em.persist(file);
        return file;
    }

    @Transactional
    public FileEntity updateFile(String fileId, FileEntity file) {
        FileEntity f = getFile(fileId);
        if(f == null || file == null)
            return null;

        file.setId(f.getId());

        // TODO: DynamicUpdate

        em.merge(file);
        return file;
    }

    @Transactional
    public void removeFile(String fileId) {
        FileEntity file = getFile(fileId);
        if(file != null)
            em.remove(file);
    }
}
