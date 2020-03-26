package io.fripointer.mappers;

import io.fripointer.lib.SharedContent;
import io.fripointer.persistence.SharedContentEntity;

public class SharedContentMapper {

    public static SharedContent fromEntity(SharedContentEntity sharedContentEntity){
        SharedContent sharedContent = new SharedContent();
        sharedContent.setId(sharedContentEntity.getId());
        sharedContent.setTimestamp(sharedContentEntity.getTimestamp());
        sharedContent.setTitle(sharedContentEntity.getTitle());
        sharedContent.setDescription(sharedContentEntity.getDescription());
        return sharedContent;
    }

    public static SharedContentEntity toEntity(SharedContent sharedContent){
        SharedContentEntity sharedContentEntity = new SharedContentEntity();
        sharedContentEntity.setTitle(sharedContent.getTitle());
        sharedContentEntity.setDescription(sharedContent.getDescription());
        return sharedContentEntity;
    }

}
