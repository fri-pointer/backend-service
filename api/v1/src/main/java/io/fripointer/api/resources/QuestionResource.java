package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Question;
import io.fripointer.services.CommentService;
import io.fripointer.services.QuestionService;
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

@Path("/questions")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private CommentService commentService;

    @Inject
    private QuestionService questionService;


    @GET
    @Operation(description = "Returns list of all questions.",
            summary = "Returns list of questions.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of questions returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Question.class))))
            })
    public Response getQuestions(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Question> questions = questionService.getQuestions(queryParameters);
        return Response
                .ok(questions.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, questions.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns question according to given id.",
            summary = "Returns question with given id.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Question returned successfully.",
                            content = @Content(schema = @Schema(implementation = Question.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested question not found.")
            })
    @Path("/{id}")
    public Response getQuestion(@PathParam("id") String id){
        Question question = questionService.getQuestion(id);
        return Response.ok(question).build();
    }


    @POST
    @Operation(description = "Creates new question.",
            summary = "Creates new question.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created question returned successfully.",
                            content = @Content(schema = @Schema(implementation = Question.class)))
            })
    public Response createQuestion(Question question){
        Question newQuestion = questionService.createQuestion(question);
        return Response.created(URI.create("/questions" + newQuestion.getId())).entity(newQuestion).build();
    }


    @PUT
    @Operation(description = "Updates existing question.",
            summary = "Updates question.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Question successfully updated.",
                            content = @Content(schema = @Schema(implementation = Question.class)))
            })
    @Path("{id}")
    public Response updateQuestion(@PathParam("id") String id, Question question){
        Question updatedQuestion = questionService.updateQuestion(id, question);
        return Response.ok(updatedQuestion).build();
    }


    @DELETE
    @Operation(description = "Deletes question.",
            summary = "Deletes question.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Question deleted.",
                            content = @Content(schema = @Schema(implementation = Question.class)))
            })
    @Path("{id}")
    public Response deleteQuestion(@PathParam("id") String id){
        questionService.removeQuestion(id);
        return Response.noContent().build();
    }

}