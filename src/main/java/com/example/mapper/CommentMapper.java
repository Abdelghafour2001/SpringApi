package com.example.mapper;

import com.example.dto.CommentsDto;
import com.example.model.Comment;
import com.example.model.Episode;
import com.example.model.Post;
import com.example.model.User;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class CommentMapper {

    public Comment map(CommentsDto commentsDto, Post post, User user) {
        Comment comment = new Comment();
        comment.setText(commentsDto.getText());
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }

    public CommentsDto mapToDto(Comment comment) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setText(comment.getText());
        commentsDto.setCreatedDate(comment.getCreatedDate().toString());
        //System.out.println(comment.getCreatedDate().toString());
        commentsDto.setPostId(comment.getPost().getPostId());
        commentsDto.setUserName(comment.getUser().getUsername());
        return commentsDto;
    }

    public Comment mapToEpisode(CommentsDto commentsDto, Episode episode, User user) {
        Comment comment = new Comment();
        comment.setText(commentsDto.getText());
        comment.setCreatedDate(Instant.now());
        comment.setEpisode(episode);
        comment.setUser(user);
        return comment;
    }
}