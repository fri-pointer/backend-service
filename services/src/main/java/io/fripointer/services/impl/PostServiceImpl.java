package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.PostEntity;
import io.fripointer.services.PostService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<PostEntity> getPosts(QueryParameters params) {
        return JPAUtils.queryEntities(em, PostEntity.class, params);
    }

    public PostEntity getPost(String postId) {
        if(postId == null)
            return null;
        return em.find(PostEntity.class, postId);
    }

    @Transactional
    public PostEntity createPost(PostEntity post) {
        if(post == null)
            return null;
        em.persist(post);
        return post;
    }

    @Transactional
    public PostEntity updatePost(String postId, PostEntity post) {
        PostEntity p = getPost(postId);
        if(p == null || post == null)
            return null;

        post.setId(p.getId());

        // TODO: Dynamic update

        em.merge(post);

        return post;
    }

    @Transactional
    public void removePost(String postId) {
        PostEntity post = getPost(postId);
        if(post != null)
            em.remove(post);
    }
}
