package io.fripointer.integrations.keycloak.apis;

import com.mjamsek.rest.exceptions.NotFoundException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class NotFoundMapper implements ResponseExceptionMapper<NotFoundException> {
    
    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == Response.Status.NOT_FOUND.getStatusCode();
    }
    
    @Override
    public NotFoundException toThrowable(Response response) {
        return new NotFoundException("keycloak.user.error.not-found");
    }
}
