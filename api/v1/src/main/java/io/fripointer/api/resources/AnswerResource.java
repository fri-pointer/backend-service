package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Answer;
import io.fripointer.lib.Comment;
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
    
    @POST
    @Path("/{id}/accept")
    public Response acceptAnswer(@PathParam("id") String id) {
        // TODO: accept answer
        return Response.noContent().build();
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
                    @ApiResponse(responseCode = "204", description = "Answer deleted.")
            })
    @Path("{id}")
    public Response deleteAnswer(@PathParam("id") String id){
        answerService.removeAnswer(id);
        return Response.noContent().build();
    }

    /*
     * ************ COMMENTS ************
     */

    @GET
    @Operation(description = "Returns list of all comments of certain answer.",
            summary = "Returns list of answer's comments.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of comments returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Comment.class))))
            })
    @Path("/{answerId}/comments")
    public Response getCommentsByAnswersId(@PathParam("answerId") String parentId){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Comment> comments = commentService.getCommentsByParentId(parentId, queryParameters);
        return Response
                .ok(comments.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, comments.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns comment according to given answer id and comment id.",
            summary = "Returns comment.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested comment not found.")
            })
    @Path("/{answerId}/comments/{comment_id}")
    public Response getCommentsByAnswerId(@PathParam("answerId") String parentId, @PathParam("comment_id") String commentId){
        Comment comment = commentService.getComment(commentId);
        return Response.ok(comment).build();
    }

    @POST
    @Operation(description = "Creates new comment for given answer.",
            summary = "Creates new comment.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{answerId}/comments")
    public Response createAnswerComment(@PathParam("answerId") String parentId, Comment comment){
        Comment newComment = commentService.createComment(parentId, comment);
        return Response
                .created(URI.create("/answers/" + parentId + "/comments/" + comment.getId()))
                .entity(newComment)
                .build();
    }


    @PUT
    @Operation(description = "Updates existing comment for given answer.",
            summary = "Updates comment.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment successfully updated.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{answerId}/comments/{commentId}")
    public Response updateAnswerComment(@PathParam("answerId") String parentId, @PathParam("commentId") String commentId, Comment comment){
        Comment updatedComment = commentService.updateComment(commentId, comment);
        return Response.ok(updatedComment).build();
    }


    @DELETE
    @Operation(description = "Deletes answer's comment.",
            summary = "Deletes comment.",
            tags = "answer",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comment deleted.")
            })
    @Path("{answerId}/comments/{commentId}")
    public Response deleteAnswerComment(@PathParam("answerId") String parentId, @PathParam("commentId") String commentId){
        commentService.removeComment(commentId);
        return Response.noContent().build();
    }

}