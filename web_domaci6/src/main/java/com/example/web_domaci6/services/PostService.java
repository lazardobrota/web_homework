package com.example.web_domaci6.services;

import com.example.web_domaci6.entities.Comment;
import com.example.web_domaci6.entities.Post;
import com.example.web_domaci6.repo.ICommentRepository;
import com.example.web_domaci6.repo.IPostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostService {

    public PostService() {
    }

    @Inject
    private IPostRepository postRepository;
    @Inject
    private ICommentRepository commentRepository;

    public Post addPost(Post post) {
        return this.postRepository.addPost(post);
    }

    public List<Post> getAllPosts() {
        return this.postRepository.getAllPosts();
    }

    public Post addCommentToPost(Comment comment) {
        this.commentRepository.addComment(comment);
        return this.postRepository.addCommenToPost(comment);
    }

    public Post getPost(int id) {
        return this.postRepository.getPost(id);
    }
}
