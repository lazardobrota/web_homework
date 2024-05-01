package com.example.web_domaci6.repo;

import com.example.web_domaci6.entities.Comment;
import com.example.web_domaci6.entities.Post;

import java.util.List;

public interface IPostRepository {
    List<Post> getAllPosts();
    Post addPost(Post post);
    Post addCommenToPost(Comment comment);
    Post getPost(int id);

}
