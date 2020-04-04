package io.fripointer.api.resources;

import io.fripointer.lib.dto.UploadSignature;
import io.fripointer.lib.dto.UploadSignatureRequest;
import io.fripointer.services.UploadService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/upload")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UploadResource {
    
    @Inject
    private UploadService uploadService;
    
    @POST
    @Path("/signature")
    public Response generateSignature(UploadSignatureRequest signatureRequest) {
        UploadSignature signature = uploadService.generateSignature(signatureRequest);
        return Response.ok(signature).build();
    }
    
}
