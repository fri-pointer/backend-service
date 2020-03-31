package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
import io.fripointer.lib.Comment;
import io.fripointer.lib.Post;
import io.fripointer.services.CommentService;
import io.fripointer.services.PostService;
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

@Path("/posts")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    // TODO: Define returned exceptions

    @Context
    private UriInfo uriInfo;

    @Inject
    private CommentService commentService;

    @Inject
    private PostService postService;


    @GET
    @Operation(description = "Returns list of all posts.",
            summary = "Returns list of posts.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of posts returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class))))
            })
    public Response getPosts(){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Post> posts = postService.getPosts(queryParameters);
        return Response
                .ok(posts.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, posts.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns post according to given id.",
            summary = "Returns post with given id.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Post returned successfully.",
                            content = @Content(schema = @Schema(implementation = Post.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested post not found.")
            })
    @Path("/{id}")
    public Response getPost(@PathParam("id") String id){
        Post post = postService.getPost(id);
        return Response.ok(post).build();
    }


    @POST
    @Operation(description = "Creates new post.",
            summary = "Creates new post.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created post returned successfully.",
                            content = @Content(schema = @Schema(implementation = Post.class)))
            })
    public Response createPost(Post post){
        Post newPost = postService.createPost(post);
        return Response.created(URI.create("/posts" + newPost.getId())).entity(newPost).build();
    }


    @PUT
    @Operation(description = "Updates existing post.",
            summary = "Updates post.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Post successfully updated.",
                            content = @Content(schema = @Schema(implementation = Post.class)))
            })
    @Path("{id}")
    public Response updatePost(@PathParam("id") String id, Post post){
        Post updatedPost = postService.updatePost(id, post);
        return Response.ok(updatedPost).build();
    }


    @DELETE
    @Operation(description = "Deletes post.",
            summary = "Deletes post.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Post deleted.")
            })
    @Path("{id}")
    public Response deletePost(@PathParam("id") String id){
        postService.removePost(id);
        return Response.noContent().build();
    }
    
    
    /*
     * ************ COMMENTS ************
     */

    @GET
    @Operation(description = "Returns list of all comments of certain post.",
            summary = "Returns list of post's comments.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of comments returned successfully.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Comment.class))))
            })
    @Path("/{postId}/comments")
    public Response getCommentsByPostsId(@PathParam("postId") String parentId){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        EntityList<Comment> comments = commentService.getCommentsByParentId(parentId, queryParameters);
        return Response
                .ok(comments.getEntityList())
                .header(HttpHeaders.X_TOTAL_COUNT, comments.getCount())
                .build();
    }


    @GET
    @Operation(description = "Returns comment according to given post id and comment id.",
            summary = "Returns comment.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Requested comment not found.")
            })
    @Path("/{postId}/comments/{comment_id}")
    public Response getCommentsByPostId(@PathParam("postId") String parentId, @PathParam("comment_id") String commentId){
        Comment comment = commentService.getComment(parentId, commentId);
        return Response.ok(comment).build();
    }


    @POST
    @Operation(description = "Creates new comment for given post.",
            summary = "Creates new comment.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Newly created comment returned successfully.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{postId}/comments")
    public Response createPostComment(@PathParam("postId") String parentId, Comment comment){
        Comment newComment = commentService.createComment(parentId, comment);
        return Response
                .created(URI.create("/posts/" + parentId + "/comments/" + comment.getId()))
                .entity(newComment)
                .build();
    }


    @PUT
    @Operation(description = "Updates existing comment for given post.",
            summary = "Updates comment.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comment successfully updated.",
                            content = @Content(schema = @Schema(implementation = Comment.class)))
            })
    @Path("{postId}/comments/{commentId}")
    public Response updatePostComment(@PathParam("postId") String parentId, @PathParam("commentId") String commentId, Comment comment){
        Comment updatedComment = commentService.updateComment(parentId, commentId, comment);
        return Response.ok(updatedComment).build();
    }


    @DELETE
    @Operation(description = "Deletes post's comment.",
            summary = "Deletes comment.",
            tags = "post",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comment deleted.")
            })
    @Path("{postId}/comments/{commentId}")
    public Response deletePostComment(@PathParam("postId") String parentId, @PathParam("commentId") String commentId){
        commentService.removeComment(parentId, commentId);
        return Response.noContent().build();
    }
    
}