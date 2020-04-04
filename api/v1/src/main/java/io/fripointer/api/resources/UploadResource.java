package io.fripointer.api.resources;

import io.fripointer.lib.dto.UploadSignature;
import io.fripointer.lib.dto.UploadSignatureRequest;
import io.fripointer.services.FileService;
import io.fripointer.services.UploadService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/upload")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UploadResource {
    
    @Inject
    private UploadService uploadService;
    
    @Inject
    private FileService fileService;
    
    @POST
    @Path("/signature")
    public Response generateSignature(UploadSignatureRequest signatureRequest) {
        UploadSignature signature = uploadService.generateSignature(signatureRequest);
        return Response.ok(signature).build();
    }
    
    @POST
    @Path("/callback")
    public Response uploadCallback(UploadSignature request) {
        fileService.finalizeFile(request.getKey());
        return Response.noContent().build();
    }
    
}
