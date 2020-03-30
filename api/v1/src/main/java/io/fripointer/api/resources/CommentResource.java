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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

}
