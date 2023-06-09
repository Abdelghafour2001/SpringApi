package com.example.repository;

import com.example.dto.CommentsDto;
import com.example.model.Comment;
import com.example.model.Episode;
import com.example.model.Post;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
    List<Comment> findAllByEpisode(Episode episode);

    Comment getCommentById(Long commentId);
}
