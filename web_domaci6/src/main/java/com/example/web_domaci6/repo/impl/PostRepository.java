package com.example.web_domaci6.repo.impl;

import com.example.web_domaci6.entities.Comment;
import com.example.web_domaci6.entities.Post;
import com.example.web_domaci6.repo.IPostRepository;
import com.example.web_domaci6.repo.MySqlRepo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostRepository extends MySqlRepo implements IPostRepository {

    //TODO getAllPosts and getPost need to catch all comments
    @Override
    public List<Post> getAllPosts() {

        List<Post> posts = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM post");

            while (resultSet.next()) {
                posts.add(new Post(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        dateToLocalDate(resultSet.getDate("dateOfPublish")),
                        resultSet.getString("author"),
                        resultSet.getString("content"),
                        new ArrayList<>()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(connection);
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
        }
        return posts;
    }

    @Override
    public Post addPost(Post post) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        LocalDate localDate = LocalDate.now();
        try{
            connection = this.newConnection();
            String[] generatedColumns = {"id"};
            preparedStatement = connection.prepareStatement("INSERT INTO post (title, dateOfPublish, author, content) VALUES (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setDate(2, localDateToDate(localDate));
            preparedStatement.setString(3, post.getAuthor());
            preparedStatement.setString(4, post.getContent());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                post.setId(resultSet.getInt(1));
                post.setDateOfPublish(localDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
        }

        return post;
    }

    //TODO This shouldn't exist
    @Override
    public Post addCommenToPost(Comment comment) {
        return getPost(comment.getPostId());
    }

    @Override
    public Post getPost(int id) {

        Post post = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM post where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                LocalDate dateOfPublish = dateToLocalDate(resultSet.getDate("dateOfPublish"));
                String author = resultSet.getString("author");
                String content = resultSet.getString("content");
                post = new Post(id, title, dateOfPublish, author, content, new ArrayList<>());

                //comments
                preparedStatement = connection.prepareStatement("SELECT * FROM comment where postId = ?");
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {

                    post.getComments().add(new Comment(
                            resultSet.getInt("id"),
                            resultSet.getInt("postId"),
                            resultSet.getString("name"),
                            resultSet.getString("comment")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
        }
        return post;
    }

    private Date localDateToDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    private LocalDate dateToLocalDate(Date date) {
        return date.toLocalDate();
    }
}
