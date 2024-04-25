package com.example.web_domaci5.repo;

import com.example.web_domaci5.entities.Comment;
import com.example.web_domaci5.entities.Post;

import java.util.List;

public interface IPostRepository {
    List<Post> getAllPosts();
    Post addPost(Post post);
    Post addCommenToPost(Comment comment);
    Post getPost(int id);

}
