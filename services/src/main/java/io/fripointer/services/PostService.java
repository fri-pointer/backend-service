package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.PostEntity;

import java.util.List;

public interface PostService {
    List<PostEntity> getPosts(QueryParameters params);
    PostEntity getPost(String postId);
    PostEntity createPost(PostEntity post);
    PostEntity updatePost(String postId, PostEntity post);
    void removePost(String postId);
}
