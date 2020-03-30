package io.fripointer.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.rest.common.HttpHeaders;
import com.mjamsek.rest.dto.EntityList;
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
public class PostSerivce {

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
                    @ApiResponse(responseCode = "204",
                            description = "Post deleted.",
                            content = @Content(schema = @Schema(implementation = Post.class)))
            })
    @Path("{id}")
    public Response deletePost(@PathParam("id") String id){
        postService.removePost(id);
        return Response.noContent().build();
    }
    
}