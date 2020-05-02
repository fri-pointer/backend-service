package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;
import io.fripointer.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/comments")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private CommentService commentService;

    @GET
    @Operation(description = "Returns list of all comments.",
            summary = "Returns list of comments.",
            tags = "comment",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of comments returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Comment.class))))
            })
    public Response getComments(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Comment> comments = commentService.getComments(queryParameters);
        return Response
                .ok(comments.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, comments.getCount())
                .build();
    }
    
    @GET
    @Path("/parent/{parentId}")
    public Response getCommentsFromParent(@PathParam("parentId") String parentId) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Comment> comments = commentService.getCommentsByParentId(parentId, queryParameters);
        return Response
            .ok(comments.getEntityList())
            .header(HttpHeaders.X_TOTAL_COUNT, comments.getCount())
            .build();
    }
    
    @GET
    @Path("/{commentId}")
    public Response getComment(@PathParam("commentId") String commentId) {
        Comment comment = commentService.getComment(commentId);
        return Response.ok(comment).build();
    }
    
    @POST
    @Path("/parent/{parentId}")
    public Response createComment(@PathParam("parentId") String parentId, Comment comment) {
        Comment createdComment = commentService.createComment(parentId, comment);
        URI createdUri = uriInfo.getBaseUriBuilder().path("comments").path(createdComment.getId()).build();
        return Response.created(createdUri).entity(createdComment).build();
    }
    
    @PUT
    @Path("/{commentId}")
    public Response updateComment(@PathParam("commentId") String commentId, Comment comment) {
        Comment updatedComment = commentService.updateComment(commentId, comment);
        URI updatedUri = uriInfo.getBaseUriBuilder().path("comments").path(updatedComment.getId()).build();
        return Response.ok(updatedComment).location(updatedUri).build();
    }
    
    @DELETE
    @Path("/{commentId}")
    public Response removeComment(@PathParam("commentId") String commentId) {
        commentService.removeComment(commentId);
        return Response.noContent().build();
    }

}
