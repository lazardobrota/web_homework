package com.example.web_domaci5.repo.impl;

import com.example.web_domaci5.entities.Comment;
import com.example.web_domaci5.entities.Post;
import com.example.web_domaci5.repo.IPostRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository implements IPostRepository {

    private final static List<Post> posts = new CopyOnWriteArrayList<>(); //todo i think static variable is not needed when class is static
    private static final AtomicInteger id = new AtomicInteger(0);

    @Override
    public List<Post> getAllPosts() {
        return new ArrayList<>(posts);
    }

    @Override
    public Post addPost(Post post) {
        post.setId(id.getAndAdd(1));
        post.setDateOfPublish(LocalDate.now());
        post.setComments(new ArrayList<>());
        posts.add(post);
        return post;
    }

    @Override
    public Post addCommenToPost(Comment comment) {
        posts.get(comment.getPostId()).getComments().add(comment);
        return posts.get(comment.getPostId());
    }

    @Override
    public Post getPost(int id) {
        return posts.get(id);
    }
}
