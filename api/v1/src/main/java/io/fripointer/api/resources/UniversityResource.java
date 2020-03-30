package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.University;
import io.fripointer.services.UniversityService;
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

@Path("/universities")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UniversityResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private UniversityService universityService;


    @GET
    @Operation(description = "Returns list of all universities.",
            summary = "Returns list of universities.",
            tags = "university",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of universities returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = University.class))))
            })
    public Response getUniversities(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<University> universities = universityService.getUniversities(queryParameters);
        return Response
                .ok(universities.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, universities.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns university according to given id.",
            summary = "Returns university with given id.",
            tags = "university",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "University returned successfully.",
                            content = @Content(schema = @Schema(implementation = University.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested university not found.")
            })
    @Path("/{id}")
    public Response getUniversity(@PathParam("id") String id){
        University university = universityService.getUniversity(id);
        return Response.ok(university).build();
    }


    @POST
    @Operation(description = "Creates new university.",
            summary = "Creates new university.",
            tags = "university",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created university returned successfully.",
                            content = @Content(schema = @Schema(implementation = University.class)))
            })
    public Response createUniversity(University university){
        University newUniversity = universityService.createUniversity(university);
        return Response.created(URI.create("/universities" + newUniversity.getId())).entity(newUniversity).build();
    }


    @PUT
    @Operation(description = "Updates existing university.",
            summary = "Updates university.",
            tags = "university",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "University successfully updated.",
                            content = @Content(schema = @Schema(implementation = University.class)))
            })
    @Path("{id}")
    public Response updateUniversity(@PathParam("id") String id, University university){
        University updatedUniversity = universityService.updateUniversity(id, university);
        return Response.ok(updatedUniversity).build();
    }


    @DELETE
    @Operation(description = "Deletes university.",
            summary = "Deletes university.",
            tags = "university",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "University deleted.",
                            content = @Content(schema = @Schema(implementation = University.class)))
            })
    @Path("{id}")
    public Response deleteUniversity(@PathParam("id") String id){
        universityService.removeUniversity(id);
        return Response.noContent().build();
    }

}