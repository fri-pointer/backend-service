package io.fripointer.mappers;

import io.fripointer.lib.Course;
import io.fripointer.persistence.CourseEntity;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public static Course fromEntityDetailed(CourseEntity courseEntity){
        Course course = fromEntity(courseEntity);

        if(courseEntity.getSharedContents() != null) {
            course.setSharedContents(courseEntity.getSharedContents()
                    .stream()
                    .map(SharedContentMapper::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            course.setSharedContents(new ArrayList<>());
        }

        if(courseEntity.getQuestions() != null) {
            course.setQuestions(courseEntity.getQuestions()
                    .stream()
                    .map(QuestionMapper::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            course.setQuestions(new ArrayList<>());
        }

        if(courseEntity.getPosts() != null) {
            course.setPosts(courseEntity.getPosts()
                    .stream()
                    .map(PostMapper::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            course.setPosts(new ArrayList<>());
        }

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
