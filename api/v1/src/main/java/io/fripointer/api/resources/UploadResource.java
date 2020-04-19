package io.fripointer.api.resources;

import com.mjamsek.rest.exceptions.dto.ExceptionResponse;
import io.fripointer.lib.dto.UploadSignature;
import io.fripointer.lib.dto.UploadSignatureRequest;
import io.fripointer.services.FileService;
import io.fripointer.services.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
    @Operation(summary = "Generate file upload url",
        description = "Generates signed url to be used for file upload to S3 instance.",
        tags = "upload",
        responses = {
            @ApiResponse(responseCode = "200", description = "URL successfully signed.",
                content = @Content(schema = @Schema(implementation = UploadSignature.class))),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication credentials",
                content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        })
    public Response generateSignature(UploadSignatureRequest signatureRequest) {
        UploadSignature signature = uploadService.generateSignature(signatureRequest);
        return Response.ok(signature).build();
    }
    
    @POST
    @Path("/callback")
    @Operation(summary = "File upload callback",
        description = "Callback endpoint to be called on successfull file upload from client.",
        tags = "upload",
        responses = {
            @ApiResponse(responseCode = "204", description = "Callback invoked.")
        })
    public Response uploadCallback(UploadSignature request) {
        fileService.finalizeFile(request.getKey());
        return Response.noContent().build();
    }
    
}
