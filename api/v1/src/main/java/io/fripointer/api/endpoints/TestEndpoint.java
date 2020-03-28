package io.fripointer.api.endpoints;

import io.fripointer.integrations.keycloak.KeycloakService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestEndpoint {
    
    @Inject
    private KeycloakService keycloakService;
    
    @GET
    public Response test() {
        return Response.ok(keycloakService.getUser("9af97bea-147a-4878-8169-16e4a53c633c")).build();
    }
    
}
