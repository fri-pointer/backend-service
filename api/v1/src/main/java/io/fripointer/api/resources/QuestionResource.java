package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;
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
                    @ApiResponse(responseCode = "204", description = "Question deleted.")
            })
    @Path("{id}")
    public Response deleteQuestion(@PathParam("id") String id){
        questionService.removeQuestion(id);
        return Response.noContent().build();
    }

    /*
     * ************ COMMENTS ************
     */

    @GET
    @Operation(description = "Returns list of all comments of certain question.",
            summary = "Returns list of question's comments.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of comments returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Comment.class))))
            })
    @Path("/{questionId}/comments")
    public Response getCommentsByQuestionsId(@PathParam("questionId") String parentId){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Comment> comments = commentService.getCommentsByParentId(parentId, queryParameters);
        return Response
                .ok(comments.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, comments.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns comment according to given question id and comment id.",
            summary = "Returns comment.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested comment not found.")
            })
    @Path("/{questionId}/comments/{comment_id}")
    public Response getCommentByQuestionId(@PathParam("questionId") String parentId, @PathParam("comment_id") String commentId){
        Comment comment = commentService.getComment(parentId, commentId);
        return Response.ok(comment).build();
    }


    @POST
    @Operation(description = "Creates new comment for given question.",
            summary = "Creates new comment.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{questionId}/comments")
    public Response createQuestionComment(@PathParam("questionId") String parentId, Comment comment){
        Comment newComment = commentService.createComment(parentId, comment);
        return Response
                .created(URI.create("/questions/" + parentId + "/comments/" + comment.getId()))
                .entity(newComment)
                .build();
    }


    @PUT
    @Operation(description = "Updates existing comment for given question.",
            summary = "Updates comment.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment successfully updated.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{questionId}/comments/{commentId}")
    public Response updateQuestionComment(@PathParam("questionId") String parentId, @PathParam("commentId") String commentId, Comment comment){
        Comment updatedComment = commentService.updateComment(parentId, commentId, comment);
        return Response.ok(updatedComment).build();
    }


    @DELETE
    @Operation(description = "Deletes question's comment.",
            summary = "Deletes comment.",
            tags = "question",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comment deleted.")
            })
    @Path("{questionId}/comments/{commentId}")
    public Response deleteQuestionComment(@PathParam("questionId") String parentId, @PathParam("commentId") String commentId){
        commentService.removeComment(parentId, commentId);
        return Response.noContent().build();
    }

}