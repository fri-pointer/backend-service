package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Course;
import io.fripointer.services.CourseService;
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

@Path("/courses")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private CourseService courseService;


    @GET
    @Operation(description = "Returns list of all courses.",
            summary = "Returns list of courses.",
            tags = "course",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of courses returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class))))
            })
    public Response getCourses(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Course> courses = courseService.getCourses(queryParameters);
        return Response
                .ok(courses.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, courses.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns course according to given id.",
            summary = "Returns course with given id.",
            tags = "course",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Course returned successfully.",
                            content = @Content(schema = @Schema(implementation = Course.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested course not found.")
            })
    @Path("/{id}")
    public Response getCourse(@PathParam("id") String id){
        Course course = courseService.getCourse(id);
        return Response.ok(course).build();
    }


    @POST
    @Operation(description = "Creates new course.",
            summary = "Creates new course.",
            tags = "course",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created course returned successfully.",
                            content = @Content(schema = @Schema(implementation = Course.class)))
            })
    public Response createCourse(Course course){
        Course newCourse = courseService.createCourse(course);
        return Response.created(URI.create("/courses" + newCourse.getId())).entity(newCourse).build();
    }


    @PUT
    @Operation(description = "Updates existing course.",
            summary = "Updates course.",
            tags = "course",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Course successfully updated.",
                            content = @Content(schema = @Schema(implementation = Course.class)))
            })
    @Path("{id}")
    public Response updateCourse(@PathParam("id") String id, Course course){
        Course updatedCourse = courseService.updateCourse(id, course);
        return Response.ok(updatedCourse).build();
    }


    @DELETE
    @Operation(description = "Deletes course.",
            summary = "Deletes course.",
            tags = "course",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Course deleted.",
                            content = @Content(schema = @Schema(implementation = Course.class)))
            })
    @Path("{id}")
    public Response deleteCourse(@PathParam("id") String id){
        courseService.removeCourse(id);
        return Response.noContent().build();
    }

}