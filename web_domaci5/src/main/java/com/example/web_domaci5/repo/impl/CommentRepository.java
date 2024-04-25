package com.example.web_domaci5.repo.impl;

import com.example.web_domaci5.entities.Comment;
import com.example.web_domaci5.entities.Post;
import com.example.web_domaci5.repo.ICommentRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CommentRepository implements ICommentRepository {

    private final static List<Comment> comments = new CopyOnWriteArrayList<>();
    private static final AtomicInteger id = new AtomicInteger(0);

    @Override
    public Comment addComment(Comment comment) {
        comment.setId(id.getAndAdd(1));
        comments.add(comment);
        return comment;
    }
}
