package io.fripointer.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import io.fripointer.persistence.CourseEntity;
import io.fripointer.services.CourseService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CourseServiceImpl implements CourseService {

    // TODO: Logging
    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    public List<CourseEntity> getCourses(QueryParameters params) {
        return JPAUtils.queryEntities(em, CourseEntity.class, params);
    }

    public CourseEntity getCourse(String courseId) {
        if(courseId == null)
            return null;
        return em.find(CourseEntity.class, courseId);
    }

    @Transactional
    public CourseEntity createCourse(CourseEntity course) {
        if(course == null)
            return null;
        em.persist(course);
        return course;
    }

    @Transactional
    public CourseEntity updateCourse(String courseId, CourseEntity course) {
        CourseEntity c = getCourse(courseId);
        if(c == null || course == null)
            return null;

        course.setId(c.getId());

        // TODO: Dynamic update

        em.merge(course);
        return course;
    }

    @Transactional
    public void removeCourse(String courseId) {
        CourseEntity course = getCourse(courseId);
        if(course != null)
            em.remove(course);
    }
}
