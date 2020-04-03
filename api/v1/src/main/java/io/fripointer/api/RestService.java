package io.fripointer.api;

import io.fripointer.api.mappers.RestExceptionMapper;
import io.fripointer.api.resources.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class RestService extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Resources
        classes.add(AnswerResource.class);
        classes.add(CommentResource.class);
        classes.add(CourseResource.class);
        classes.add(FacultyResource.class);
        classes.add(FileResource.class);
        classes.add(PostResource.class);
        classes.add(QuestionResource.class);
        classes.add(SharedContentResource.class);
        classes.add(StudentProgramResource.class);
        classes.add(UniversityResource.class);

        // Mappers
        classes.add(RestExceptionMapper.class);

        return classes;
    }
}
