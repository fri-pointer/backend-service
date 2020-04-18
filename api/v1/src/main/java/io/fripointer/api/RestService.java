package io.fripointer.api;

import io.fripointer.api.mappers.RestExceptionMapper;
import io.fripointer.api.resources.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
@OpenAPIDefinition(
    info = @Info(title = "FRI Pointer backend service", version = "1.0.0", description = "FRI Pointer REST API."),
    servers = @Server(url = "http://localhost:8080/v1")
)
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
        classes.add(UploadResource.class);

        // Mappers
        classes.add(RestExceptionMapper.class);

        return classes;
    }
}
