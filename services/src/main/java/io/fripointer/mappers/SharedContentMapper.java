package io.fripointer.mappers;

import io.fripointer.lib.SharedContent;
import io.fripointer.persistence.SharedContentEntity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SharedContentMapper {

    public static SharedContent fromEntity(SharedContentEntity sharedContentEntity){
        SharedContent sharedContent = new SharedContent();
        sharedContent.setId(sharedContentEntity.getId());
        sharedContent.setTimestamp(sharedContentEntity.getTimestamp());
        sharedContent.setTitle(sharedContentEntity.getTitle());
        sharedContent.setDescription(sharedContentEntity.getDescription());
        return sharedContent;
    }

    public static SharedContent fromEntityDetailed(SharedContentEntity sharedContentEntity){
        SharedContent sharedContent = fromEntity(sharedContentEntity);

        if(sharedContentEntity.getFiles() != null) {
            sharedContent.setFiles(sharedContentEntity.getFiles()
                    .stream()
                    .map(FileMapper::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            sharedContent.setFiles(new ArrayList<>());
        }

        return sharedContent;
    }

    public static SharedContentEntity toEntity(SharedContent sharedContent){
        SharedContentEntity sharedContentEntity = new SharedContentEntity();
        sharedContentEntity.setTitle(sharedContent.getTitle());
        sharedContentEntity.setDescription(sharedContent.getDescription());
        return sharedContentEntity;
    }

}
