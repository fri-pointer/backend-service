package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.StudentProgram;
import io.fripointer.services.StudentProgramService;
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

@Path("/studentPrograms")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentProgramResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private StudentProgramService studentProgramService;


    @GET
    @Operation(description = "Returns list of all student programs.",
            summary = "Returns list of student programs.",
            tags = "studentProgram",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of student programs returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentProgram.class))))
            })
    public Response getStudentPrograms(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<StudentProgram> studentPrograms = studentProgramService.getStudentPrograms(queryParameters);
        return Response
                .ok(studentPrograms.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, studentPrograms.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns student program according to given id.",
            summary = "Returns student program with given id.",
            tags = "studentProgram",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Student program returned successfully.",
                            content = @Content(schema = @Schema(implementation = StudentProgram.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested student program not found.")
            })
    @Path("/{id}")
    public Response getStudentProgram(@PathParam("id") String id){
        StudentProgram studentProgram = studentProgramService.getStudentProgram(id);
        return Response.ok(studentProgram).build();
    }


    @POST
    @Operation(description = "Creates new student program.",
            summary = "Creates new student program.",
            tags = "student program",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created student program returned successfully.",
                            content = @Content(schema = @Schema(implementation = StudentProgram.class)))
            })
    public Response createStudentProgram(StudentProgram studentProgram){
        StudentProgram newStudentProgram = studentProgramService.createStudentProgram(studentProgram);
        return Response.created(URI.create("/studentPrograms" + newStudentProgram.getId())).entity(newStudentProgram).build();
    }


    @PUT
    @Operation(description = "Updates existing student program.",
            summary = "Updates student program.",
            tags = "studentProgram",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Student program successfully updated.",
                            content = @Content(schema = @Schema(implementation = StudentProgram.class)))
            })
    @Path("{id}")
    public Response updateStudentProgram(@PathParam("id") String id, StudentProgram studentProgram){
        StudentProgram updatedStudentProgram = studentProgramService.updateStudentProgram(id, studentProgram);
        return Response.ok(updatedStudentProgram).build();
    }


    @DELETE
    @Operation(description = "Deletes student program.",
            summary = "Deletes student program.",
            tags = "studentProgram",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Student program deleted.")
            })
    @Path("{id}")
    public Response deleteStudentProgram(@PathParam("id") String id){
        studentProgramService.removeStudentProgram(id);
        return Response.noContent().build();
    }

}