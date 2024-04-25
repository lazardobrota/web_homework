package com.example.web_domaci5.repo;

import com.example.web_domaci5.entities.Comment;
import com.example.web_domaci5.entities.Post;

public interface ICommentRepository {
    Comment addComment(Comment comment);
}
