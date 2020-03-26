package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Post;

public interface PostService {
    EntityList<Post> getPosts(QueryParameters params);
    Post getPost(String postId);
    Post createPost(Post post);
    Post updatePost(String postId, Post post);
    void removePost(String postId);
}
