package io.fripointer.api;

import io.fripointer.api.endpoints.TestEndpoint;
import io.fripointer.api.mappers.RestExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class RestService extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        // Endpoints
        classes.add(TestEndpoint.class);
        
        // Mappers
        classes.add(RestExceptionMapper.class);
        
        return classes;
    }
}
