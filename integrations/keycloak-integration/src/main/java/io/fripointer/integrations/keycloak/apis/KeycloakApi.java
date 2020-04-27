package io.fripointer.integrations.keycloak.apis;

import com.mjamsek.auth.keycloak.client.KeycloakClient;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.integrations.keycloak.lib.User;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface KeycloakApi {
    
    default String getAuthorizationHeader() {
        return "Bearer " + KeycloakClient.getServiceToken();
    }
    
    @POST
    @Path("/admin/realms/{realm}/users")
    @ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "{getAuthorizationHeader}")
    Response createUser(
        @PathParam("realm") String realm,
        User user
    );
    
    @PUT
    @Path("/admin/realms/{realm}/users/{userId}")
    @ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "{getAuthorizationHeader}")
    Response updateUser(
        @PathParam("realm") String realm,
        @PathParam("userId") String userId,
        User user
    ) throws NotFoundException;
    
    @GET
    @Path("/admin/realms/{realm}/users")
    @ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "{getAuthorizationHeader}")
    List<User> getUsers(
        @PathParam("realm") String realm,
        @BeanParam Query query
    );
    
    @GET
    @Path("/admin/realms/{realm}/users/count")
    @ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "{getAuthorizationHeader}")
    int getUsersCount(
        @PathParam("realm") String realm,
        @BeanParam Query query
    );
    
    @GET
    @Path("/admin/realms/{realm}/users/{userId}")
    @ClientHeaderParam(name = HttpHeaders.AUTHORIZATION, value = "{getAuthorizationHeader}")
    User getUser(
        @PathParam("realm") String realm,
        @PathParam("userId") String userId
    ) throws NotFoundException;
    
}
