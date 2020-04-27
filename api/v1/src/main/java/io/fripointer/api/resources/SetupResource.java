package io.fripointer.api.resources;

import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import io.fripointer.lib.ActionCompleted;
import io.fripointer.lib.dto.UserSetupRequest;
import io.fripointer.services.SetupService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/setup")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecureResource
public class SetupResource {
    
    @Inject
    private SetupService setupService;
    
    @POST
    @AuthenticatedAllowed
    public Response completeSetup(UserSetupRequest request) {
        ActionCompleted actionStatus = setupService.completeUserSetup(request);
        return Response.ok(actionStatus).build();
    }
    
}
