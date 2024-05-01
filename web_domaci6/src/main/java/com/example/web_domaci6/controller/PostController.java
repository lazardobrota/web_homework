package com.example.web_domaci6.controller;

import com.example.web_domaci6.entities.Comment;
import com.example.web_domaci6.entities.Post;
import com.example.web_domaci6.services.PostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/posts")
public class PostController {

    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPost() {
        return Response.ok(this.postService.getAllPosts()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post addPost(@Valid Post post) {
        return  this.postService.addPost(post);
    }

    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public Post addCommentToPost(@Valid Comment comment) {
        return  this.postService.addCommentToPost(comment);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("id") int id) {
        return this.postService.getPost(id);
    }
}
