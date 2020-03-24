package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.FileEntity;

import java.util.List;

public interface FileService {
    List<FileEntity> getFiles(QueryParameters params);
    FileEntity getFile(String fileId);
    FileEntity createFile(FileEntity file);
    FileEntity updateFile(String fileId, FileEntity file);
    void removeFile(String fileId);
}
