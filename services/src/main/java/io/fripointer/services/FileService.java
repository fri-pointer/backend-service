package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.File;

public interface FileService {
    EntityList<File> getFiles(QueryParameters params);
    File getFile(String fileId);
    File createFile(File file);
    File updateFile(String fileId, File file);
    void removeFile(String fileId);
    
    void createFileStub(File file);
    void finalizeFile(String fileKey);
}
