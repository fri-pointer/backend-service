package io.fripointer.integrations.keycloak.apis;

import io.fripointer.integrations.keycloak.lib.User;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface KeycloakApi {
    
    @POST
    @Path("/admin/{realm}/users")
    Response createUser(
        @PathParam("realm") String realm,
        @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
        User user
    );
    
    @PUT
    @Path("/admin/{realm}/users/{userId}")
    Response updateUser(
        @PathParam("realm") String realm,
        @PathParam("userId") String userId,
        @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,
        User user
    );
    
    @GET
    @Path("/admin/{realm}/users")
    List<User> getUsers(
        @PathParam("realm") String realm,
        @BeanParam Query query,
        @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader
    );
    
    @GET
    @Path("/admin/{realm}/users/count")
    int getUsersCount(
        @PathParam("realm") String realm,
        @BeanParam Query query,
        @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader
    );
    
    @GET
    @Path("/admin/{realm}/users/{userId}")
    User getUser(
        @PathParam("realm") String realm,
        @PathParam("userId") String userId,
        @HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader
    );
    
}
