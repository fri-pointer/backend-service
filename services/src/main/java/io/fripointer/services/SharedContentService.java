package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.SharedContent;
import io.fripointer.persistence.SharedContentEntity;

import java.util.List;

public interface SharedContentService {
    EntityList<SharedContent> getSharedContents(QueryParameters params);
    SharedContent getSharedContent(String sharedContentId);
    SharedContent createSharedContent(SharedContent sharedContent);
    SharedContent updateSharedContent(String sharedContentId, SharedContent sharedContent);
    void removeSharedContent(String sharedContentId);
}
