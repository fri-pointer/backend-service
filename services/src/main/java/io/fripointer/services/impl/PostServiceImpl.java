package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Post;
import io.fripointer.mappers.PostMapper;
import io.fripointer.persistence.PostEntity;
import io.fripointer.services.PostService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    private static final Logger log = LogManager.getLogger(PostServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<Post> getPosts(QueryParameters params) {
        List<Post> posts = JPAUtils.queryEntities(em, PostEntity.class, params)
                .stream()
                .map(PostMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, PostEntity.class, params);
        return new EntityList<>(posts, count);
    }

    @Override
    public Post getPost(String postId) {
        if(postId == null)
            return null;
        return PostMapper.fromEntity(getPostEntity(postId));
    }

    @Override
    public Post createPost(Post post) {
        if(post == null) {
            log.info("Post not created - input is null");
            return null;
        }

        PostEntity postEntity = PostMapper.toEntity(post);

        em.getTransaction().begin();
        em.persist(postEntity);
        em.getTransaction().commit();

        log.info("Post with id {} created", post.getId());

        return PostMapper.fromEntity(postEntity);
    }

    @Override
    public Post updatePost(String postId, Post post) {
        if(post == null){
            log.info("Post not created - input is null");
            return null;
        }

        PostEntity postEntity = getPostEntity(postId);
        if(postEntity == null) {
            log.info("Post with id {} not found", postId);
            return null;
        }

        try {
            em.getTransaction().begin();
            postEntity.setTitle(post.getTitle());
            postEntity.setContent(post.getContent());
            em.getTransaction().commit();
            log.info("Post with id {} updated successfully", postId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating Post with id {}", postId);
        }
        return PostMapper.fromEntity(postEntity);
    }

    @Override
    public void removePost(String postId) {
        PostEntity postEntity = getPostEntity(postId);
        if(postEntity != null) {
            em.getTransaction().begin();
            em.remove(postEntity);
            em.getTransaction().commit();
            log.info("Post with id {} removed", postId);
        }
    }

    private PostEntity getPostEntity(String postId){
        if(postId == null)
            return null;
        return em.find(PostEntity.class, postId);

    }
}
