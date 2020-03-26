package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Course;
import io.fripointer.mappers.CourseMapper;
import io.fripointer.persistence.CourseEntity;
import io.fripointer.services.CourseService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CourseServiceImpl implements CourseService {

    private static final Logger log = LogManager.getLogger(CourseServiceImpl.class.getName());

    // TODO: Custom exceptions (Not modified, entity required etc.)

    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;

    @Override
    public EntityList<Course> getCourses(QueryParameters params) {
        List<Course> courses = JPAUtils.queryEntities(em, CourseEntity.class, params)
                .stream()
                .map(CourseMapper::fromEntity)
                .collect(Collectors.toList());
        long count = JPAUtils.queryEntitiesCount(em, CourseEntity.class, params);
        return new EntityList<>(courses, count);
    }

    @Override
    public Course getCourse(String courseId) {
        if(courseId == null)
            return null;
        return CourseMapper.fromEntity(getCourseEntity(courseId));
    }

    @Override
    public Course createCourse(Course course) {

        if(course == null) {
            log.info("Course not created - input is null");
            return null;
        }

        CourseEntity courseEntity = CourseMapper.toEntity(course);

        em.getTransaction().begin();
        em.persist(courseEntity);
        em.getTransaction().commit();

        log.info("Course with id {} created", courseEntity.getId());

        return CourseMapper.fromEntity(courseEntity);
    }

    @Override
    public Course updateCourse(String courseId, Course course) {

        if(course == null){
            log.info("Course not created - input is null");
            return null;
        }

        CourseEntity courseEntity = getCourseEntity(courseId);
        if(courseEntity == null) {
            log.info("Course with id {} not found", courseId);
            return null;
        }

        try {
            em.getTransaction().begin();
            courseEntity.setName(course.getName());
            courseEntity.setAbbreviation(course.getAbbreviation());
            courseEntity.setYear(course.getYear());
            courseEntity.setOwner(course.getOwner());
            em.getTransaction().commit();
            log.info("Course with id {} updated successfully", courseId);
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.info("Error while updating Course with id {}", courseId);
        }
        return course;
    }

    @Override
    public void removeCourse(String courseId) {
        CourseEntity courseEntity = getCourseEntity(courseId);
        if(courseEntity != null) {
            em.getTransaction().begin();
            em.remove(courseEntity);
            em.getTransaction().commit();
            log.info("Course with id {} removed", courseId);
        }
    }

    private CourseEntity getCourseEntity(String courseId){
        if(courseId == null)
            return null;
        return em.find(CourseEntity.class, courseId);
    }

}
