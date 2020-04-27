package io.fripointer.integrations.keycloak.apis;

import com.mjamsek.rest.exceptions.UnauthorizedException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class UnauthorizedMapper implements ResponseExceptionMapper<UnauthorizedException> {
    
    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == Response.Status.UNAUTHORIZED.getStatusCode();
    }
    
    @Override
    public UnauthorizedException toThrowable(Response response) {
        return new UnauthorizedException("keycloak.service.error.unauthorized");
    }
}
