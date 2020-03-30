package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Answer;
import io.fripointer.services.AnswerService;
import io.fripointer.services.CommentService;
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

@Path("/answers")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnswerResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private CommentService commentService;

    @Inject
    private AnswerService answerService;


    @GET
    @Operation(description = "Returns list of all answers.",
            summary = "Returns list of answers.",
            tags = "answer",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "List of answers returned successfully.",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class))))
            })
    public Response getAnswers(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Answer> answers = answerService.getAnswers(queryParameters);
        return Response
                .ok(answers.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, answers.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns answer according to given id.",
            summary = "Returns answer with given id.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Answer returned successfully.",
                            content = @Content(schema = @Schema(implementation = Answer.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested answer not found.")
            })
    @Path("/{id}")
    public Response getAnswer(@PathParam("id") String id){
        Answer answer = answerService.getAnswer(id);
        return Response.ok(answer).build();
    }


    @POST
    @Operation(description = "Creates new answer.",
            summary = "Creates new answer.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created answer returned successfully.",
                            content = @Content(schema = @Schema(implementation = Answer.class)))
            })
    public Response createAnswer(Answer answer){
        Answer newAnswer = answerService.createAnswer(answer);
        return Response.created(URI.create("/answers" + newAnswer.getId())).entity(newAnswer).build();
    }


    @PUT
    @Operation(description = "Updates existing answer.",
            summary = "Updates answer.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Answer successfully updated.",
                            content = @Content(schema = @Schema(implementation = Answer.class)))
            })
    @Path("{id}")
    public Response updateAnswer(@PathParam("id") String id, Answer answer){
        Answer updatedAnswer = answerService.updateAnswer(id, answer);
        return Response.ok(updatedAnswer).build();
    }


    @DELETE
    @Operation(description = "Deletes answer.",
            summary = "Deletes answer.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Answer deleted.",
                            content = @Content(schema = @Schema(implementation = Answer.class)))
            })
    @Path("{id}")
    public Response deleteAnswer(@PathParam("id") String id){
        answerService.removeAnswer(id);
        return Response.noContent().build();
    }

}