package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.SharedContentEntity;

import java.util.List;

public interface SharedContentService {
    List<SharedContentEntity> getSharedContents(QueryParameters params);
    SharedContentEntity getSharedContent(String sharedContentId);
    SharedContentEntity createSharedContent(SharedContentEntity sharedContent);
    SharedContentEntity updateSharedContent(String sharedContentId, SharedContentEntity sharedContent);
    void removeSharedContent(String sharedContentId);
}
