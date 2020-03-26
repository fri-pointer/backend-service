package io.fripointer.mappers;

import io.fripointer.lib.File;
import io.fripointer.persistence.FileEntity;

public class FileMapper {

    public static File fromEntity(FileEntity fileEntity){
        File file = new File();
        file.setId(fileEntity.getId());
        file.setTimestamp(fileEntity.getTimestamp());
        file.setName(fileEntity.getName());
        file.setPath(fileEntity.getPath());
        file.setFileType(fileEntity.getFileType());
        return file;
    }

    public static FileEntity toEntity(File file){
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getName());
        fileEntity.setPath(file.getPath());
        fileEntity.setFileType(file.getFileType());
        return fileEntity;
    }

}
