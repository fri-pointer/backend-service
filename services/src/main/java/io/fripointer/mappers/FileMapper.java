package io.fripointer.mappers;

import io.fripointer.lib.File;
import io.fripointer.persistence.FileEntity;

public class FileMapper {

    public static File fromEntity(FileEntity fileEntity){
        File file = new File();
        file.setId(fileEntity.getId());
        file.setTimestamp(fileEntity.getTimestamp());
        file.setName(fileEntity.getName());
        file.setLocation(fileEntity.getLocation());
        file.setMimeType(fileEntity.getMimeType());
        return file;
    }

    public static FileEntity toEntity(File file){
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getName());
        fileEntity.setLocation(file.getLocation());
        fileEntity.setMimeType(file.getMimeType());
        return fileEntity;
    }

}
