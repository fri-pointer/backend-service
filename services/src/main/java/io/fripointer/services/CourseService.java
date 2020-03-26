package io.fripointer.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Course;
import io.fripointer.persistence.CourseEntity;

public interface CourseService {
    EntityList<Course> getCourses(QueryParameters params);
    Course getCourse(String courseId);
    Course createCourse(Course course);
    Course updateCourse(String courseId, Course course);
    void removeCourse(String courseId);
}
