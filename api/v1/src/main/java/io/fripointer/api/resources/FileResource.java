package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.File;
import io.fripointer.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/files")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private FileService fileService;


    @GET
    @Operation(description = "Returns list of all files.",
            summary = "Returns list of files.",
            tags = "file",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of files returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = File.class))))
            })
    public Response getFiles(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<File> files = fileService.getFiles(queryParameters);
        return Response
                .ok(files.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, files.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns file according to given id.",
            summary = "Returns file with given id.",
            tags = "file",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "File returned successfully.",
                            content = @Content(schema = @Schema(implementation = File.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested file not found.")
            })
    @Path("/{id}")
    public Response getFile(@PathParam("id") String id){
        File file = fileService.getFile(id);
        return Response.ok(file).build();
    }


    @POST
    @Operation(description = "Creates new file.",
            summary = "Creates new file.",
            tags = "file",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created file returned successfully.",
                            content = @Content(schema = @Schema(implementation = File.class)))
            })
    public Response createFile(File file){
        File newFile = fileService.createFile(file);
        return Response.created(URI.create("/files" + newFile.getId())).entity(newFile).build();
    }


    @PUT
    @Operation(description = "Updates existing file.",
            summary = "Updates file.",
            tags = "file",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "File successfully updated.",
                            content = @Content(schema = @Schema(implementation = File.class)))
            })
    @Path("{id}")
    public Response updateFile(@PathParam("id") String id, File file){
        File updatedFile = fileService.updateFile(id, file);
        return Response.ok(updatedFile).build();
    }


    @DELETE
    @Operation(description = "Deletes file.",
            summary = "Deletes file.",
            tags = "file",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "File deleted.",
                            content = @Content(schema = @Schema(implementation = File.class)))
            })
    @Path("{id}")
    public Response deleteFile(@PathParam("id") String id){
        fileService.removeFile(id);
        return Response.noContent().build();
    }

}