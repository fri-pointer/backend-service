package io.fripointer.integrations.keycloak.apis;

import com.mjamsek.rest.exceptions.ForbiddenException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class ForbiddenMapper implements ResponseExceptionMapper<ForbiddenException> {
    
    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == Response.Status.FORBIDDEN.getStatusCode();
    }
    
    @Override
    public ForbiddenException toThrowable(Response response) {
        return new ForbiddenException("keycloak.service.error.forbidden");
    }
}
