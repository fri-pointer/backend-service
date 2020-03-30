package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Faculty;
import io.fripointer.services.FacultyService;
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

@Path("/faculties")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FacultyResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private FacultyService facultyService;


    @GET
    @Operation(description = "Returns list of all faculties.",
            summary = "Returns list of faculties.",
            tags = "faculty",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of faculties returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Faculty.class))))
            })
    public Response getFaculties(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Faculty> faculties = facultyService.getFaculties(queryParameters);
        return Response
                .ok(faculties.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, faculties.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns faculty according to given id.",
            summary = "Returns faculty with given id.",
            tags = "faculty",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Faculty returned successfully.",
                            content = @Content(schema = @Schema(implementation = Faculty.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested faculty not found.")
            })
    @Path("/{id}")
    public Response getFaculty(@PathParam("id") String id){
        Faculty faculty = facultyService.getFaculty(id);
        return Response.ok(faculty).build();
    }


    @POST
    @Operation(description = "Creates new faculty.",
            summary = "Creates new faculty.",
            tags = "faculty",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created faculty returned successfully.",
                            content = @Content(schema = @Schema(implementation = Faculty.class)))
            })
    public Response createFaculty(Faculty faculty){
        Faculty newFaculty = facultyService.createFaculty(faculty);
        return Response.created(URI.create("/faculties" + newFaculty.getId())).entity(newFaculty).build();
    }


    @PUT
    @Operation(description = "Updates existing faculty.",
            summary = "Updates faculty.",
            tags = "faculty",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Faculty successfully updated.",
                            content = @Content(schema = @Schema(implementation = Faculty.class)))
            })
    @Path("{id}")
    public Response updateFaculty(@PathParam("id") String id, Faculty faculty){
        Faculty updatedFaculty = facultyService.updateFaculty(id, faculty);
        return Response.ok(updatedFaculty).build();
    }


    @DELETE
    @Operation(description = "Deletes faculty.",
            summary = "Deletes faculty.",
            tags = "faculty",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Faculty deleted.",
                            content = @Content(schema = @Schema(implementation = Faculty.class)))
            })
    @Path("{id}")
    public Response deleteFaculty(@PathParam("id") String id){
        facultyService.removeFaculty(id);
        return Response.noContent().build();
    }

}