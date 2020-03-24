package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import io.fripointer.persistence.CourseEntity;

import java.util.List;

public interface CourseService {
    List<CourseEntity> getCourses(QueryParameters params);
    CourseEntity getCourse(String courseId);
    CourseEntity createCourse(CourseEntity course);
    CourseEntity updateCourse(String courseId, CourseEntity course);
    void removeCourse(String courseId);
}
