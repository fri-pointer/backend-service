package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;
import io.fripointer.lib.SharedContent;
import io.fripointer.services.CommentService;
import io.fripointer.services.SharedContentService;
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

@Path("/sharedContents")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SharedContentResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private CommentService commentService;

    @Inject
    private SharedContentService sharedContentService;


    @GET
    @Operation(description = "Returns list of all shared contents.",
            summary = "Returns list of shared contents.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of shared contents returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SharedContent.class))))
            })
    public Response getSharedContents(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<SharedContent> sharedContents = sharedContentService.getSharedContents(queryParameters);
        return Response
                .ok(sharedContents.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, sharedContents.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns shared content according to given id.",
            summary = "Returns shared content with given id.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Shared content returned successfully.",
                            content = @Content(schema = @Schema(implementation = SharedContent.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested shared content not found.")
            })
    @Path("/{id}")
    public Response getSharedContent(@PathParam("id") String id){
        SharedContent sharedContent = sharedContentService.getSharedContent(id);
        return Response.ok(sharedContent).build();
    }


    @POST
    @Operation(description = "Creates new shared content.",
            summary = "Creates new shared content.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created shared content returned successfully.",
                            content = @Content(schema = @Schema(implementation = SharedContent.class)))
            })
    public Response createSharedContent(SharedContent sharedContent){
        SharedContent newSharedContent = sharedContentService.createSharedContent(sharedContent);
        return Response.created(URI.create("/sharedContents" + newSharedContent.getId())).entity(newSharedContent).build();
    }


    @PUT
    @Operation(description = "Updates existing shared content.",
            summary = "Updates sharedContent.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "SharedContent successfully updated.",
                            content = @Content(schema = @Schema(implementation = SharedContent.class)))
            })
    @Path("{id}")
    public Response updateSharedContent(@PathParam("id") String id, SharedContent sharedContent){
        SharedContent updatedSharedContent = sharedContentService.updateSharedContent(id, sharedContent);
        return Response.ok(updatedSharedContent).build();
    }


    @DELETE
    @Operation(description = "Deletes shared content.",
            summary = "Deletes shared content.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Shared content deleted.")
            })
    @Path("{id}")
    public Response deleteSharedContent(@PathParam("id") String id){
        sharedContentService.removeSharedContent(id);
        return Response.noContent().build();
    }


    /*
     * ************ COMMENTS ************
     */

    @GET
    @Operation(description = "Returns list of all comments of certain sharedContent.",
            summary = "Returns list of sharedContent's comments.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of comments returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Comment.class))))
            })
    @Path("/{sharedContentId}/comments")
    public Response getCommentsBySharedContentsId(@PathParam("sharedContentId") String parentId){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Comment> comments = commentService.getCommentsByParentId(parentId, queryParameters);
        return Response
                .ok(comments.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, comments.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns comment according to given sharedContent id and comment id.",
            summary = "Returns comment.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested comment not found.")
            })
    @Path("/{sharedContentId}/comments/{comment_id}")
    public Response getCommentsBySharedContentId(@PathParam("sharedContentId") String parentId, @PathParam("comment_id") String commentId){
        Comment comment = commentService.getComment(parentId, commentId);
        return Response.ok(comment).build();
    }


    @POST
    @Operation(description = "Creates new comment for given sharedContent.",
            summary = "Creates new comment.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{sharedContentId}/comments")
    public Response createSharedContentComment(@PathParam("sharedContentId") String parentId, Comment comment){
        Comment newComment = commentService.createComment(parentId, comment);
        return Response
                .created(URI.create("/sharedContents/" + parentId + "/comments/" + comment.getId()))
                .entity(newComment)
                .build();
    }


    @PUT
    @Operation(description = "Updates existing comment for given sharedContent.",
            summary = "Updates comment.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment successfully updated.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{sharedContentId}/comments/{commentId}")
    public Response updateSharedContentComment(@PathParam("sharedContentId") String parentId, @PathParam("commentId") String commentId, Comment comment){
        Comment updatedComment = commentService.updateComment(parentId, commentId, comment);
        return Response.ok(updatedComment).build();
    }


    @DELETE
    @Operation(description = "Deletes sharedContent's comment.",
            summary = "Deletes comment.",
            tags = "sharedContent",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comment deleted.")
            })
    @Path("{sharedContentId}/comments/{commentId}")
    public Response deleteSharedContentComment(@PathParam("sharedContentId") String parentId, @PathParam("commentId") String commentId){
        commentService.removeComment(parentId, commentId);
        return Response.noContent().build();
    }

}