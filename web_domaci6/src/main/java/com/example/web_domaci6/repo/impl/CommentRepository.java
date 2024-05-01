package com.example.web_domaci6.repo.impl;

import com.example.web_domaci6.entities.Comment;
import com.example.web_domaci6.repo.ICommentRepository;
import com.example.web_domaci6.repo.MySqlRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRepository extends MySqlRepo implements ICommentRepository {

    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comment (postId, name, comment) VALUES (?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, comment.getPostId());
            preparedStatement.setString(2, comment.getName());
            preparedStatement.setString(3, comment.getComment());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
        }
        return comment;
    }
}
