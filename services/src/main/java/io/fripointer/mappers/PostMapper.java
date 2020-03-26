package io.fripointer.mappers;

import io.fripointer.lib.Post;
import io.fripointer.persistence.PostEntity;

public class PostMapper {

    public static Post fromEntity(PostEntity postEntity){
        Post post = new Post();
        post.setId(postEntity.getId());
        post.setTimestamp(postEntity.getTimestamp());
        post.setTitle(postEntity.getTitle());
        post.setContent(postEntity.getContent());
        return post;
    }

    public static PostEntity toEntity(Post post){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        return postEntity;
    }

}
