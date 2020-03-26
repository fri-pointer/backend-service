package io.fripointer.mappers;

import io.fripointer.lib.Course;
import io.fripointer.persistence.CourseEntity;

public class CourseMapper {

    public static Course fromEntity(CourseEntity courseEntity){
        Course course = new Course();
        course.setId(courseEntity.getId());
        course.setTimestamp(courseEntity.getTimestamp());
        course.setName(courseEntity.getName());
        course.setAbbreviation(courseEntity.getAbbreviation());
        course.setYear(courseEntity.getYear());
        course.setOwner(courseEntity.getOwner());
        return course;
    }

    public static CourseEntity toEntity(Course course){
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(course.getName());
        courseEntity.setAbbreviation(course.getAbbreviation());
        courseEntity.setYear(course.getYear());
        courseEntity.setOwner(course.getOwner());
        return courseEntity;
    }
}
