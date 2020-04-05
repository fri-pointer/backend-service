package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.File;
import io.fripointer.mappers.FileMapper;
import io.fripointer.persistence.FileEntity;
import io.fripointer.services.FileService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    private static final Logger log = LogManager.getLogger(FileServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<File> getFiles(QueryParameters params) {
        List<File> files = JPAUtils.queryEntities(em, FileEntity.class, params)
                .stream()
                .map(FileMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, FileEntity.class, params);
        return new EntityList<>(files, count);
    }

    @Override
    public File getFile(String fileId) {
        if(fileId == null)
            return null;
        return FileMapper.fromEntity(getFileEntity(fileId));
    }

    @Override
    public File createFile(File file) {
        if(file == null) {
            log.info("File not created - input is null");
            return null;
        }

        FileEntity fileEntity = FileMapper.toEntity(file);

        em.getTransaction().begin();
        em.persist(fileEntity);
        em.getTransaction().commit();

        log.info("File with id {} created", file.getId());

        return FileMapper.fromEntity(fileEntity);
    }

    @Override
    public File updateFile(String fileId, File file) {
        if(file == null){
            log.info("File not created - input is null");
            return null;
        }

        FileEntity fileEntity = getFileEntity(fileId);
        if(fileEntity == null) {
            log.info("File with id {} not found", fileId);
            return null;
        }

        try {
            em.getTransaction().begin();
            fileEntity.setName(file.getName());
            fileEntity.setLocation(file.getLocation());
            fileEntity.setMimeType(file.getMimeType());
            em.getTransaction().commit();
            log.info("File with id {} updated successfully", fileId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating File with id {}", fileId);
        }
        return FileMapper.fromEntity(fileEntity);
    }

    @Override
    public void removeFile(String fileId) {
        FileEntity fileEntity = getFileEntity(fileId);
        if(fileEntity != null) {
            em.getTransaction().begin();
            em.remove(fileEntity);
            em.getTransaction().commit();
            log.info("File with id {} removed", fileId);
        }
    }
    
    @Override
    public void createFileStub(File file) {
        
        FileEntity entity = new FileEntity();
        entity.setUploaded(false);
        entity.setMimeType(file.getMimeType());
        entity.setFileExtension(file.getFileExtension());
        entity.setName(file.getName());
        entity.setLocation(file.getLocation());
        entity.setKey(file.getKey());
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public void finalizeFile(String fileKey) {
        TypedQuery<FileEntity> query = em.createNamedQuery(FileEntity.FIND_BY_KEY, FileEntity.class);
        query.setParameter("key", fileKey);
        try {
            FileEntity entity = query.getSingleResult();
            entity.setUploaded(true);
            try {
                em.getTransaction().begin();
                em.merge(entity);
                em.getTransaction().commit();
            } catch (PersistenceException e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            }
        } catch (NoResultException ignored) {
            // If no match found, ignore callback
        }
    }
    
    private FileEntity getFileEntity(String fileId){
        if(fileId == null)
            return null;
        return em.find(FileEntity.class, fileId);
    }

}
