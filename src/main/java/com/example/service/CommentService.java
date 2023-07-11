package com.example.service;

import com.example.dto.CommentsDto;
import com.example.model.*;
import com.example.repository.EpisodeRepository;
import com.example.util.PostNotFoundException;
import com.example.util.SpringRedditException;
import com.example.mapper.CommentMapper;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailService mailService;
    private final AnimeService animeService;
    private final EpisodeRepository episodeRepository;

    public void save(CommentsDto commentsDto) {
        //hna ndir if else
        Post post = new Post();
        Comment comment = new Comment();
        User user = new User();
    if(commentsDto.getPostId()!=null) {
        post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        user = authService.getCurrentUser();
        String message = post.getUser().getUsername() + " posted a comment on your post." + POST_URL;
        sendCommentNotification(message, post.getUser());
        comment = commentMapper.map(commentsDto, post, user);
    }else if (commentsDto.getEpisodeId()!=null) {
         user = authService.getCurrentUser();
         Episode ep = new Episode();
         ep.setEpisodeId(commentsDto.getEpisodeId());
        episodeRepository.save(ep);
         ep.setEpisodeId(commentsDto.getEpisodeId());
        comment = commentMapper.mapToEpisode(commentsDto,ep , user);

    }
        commentRepository.save(comment);

    }

    public void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream()
                .map(commentMapper::mapToDto).toList();

    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shhh")) {
            throw new SpringRedditException("Comments contains unacceptable language");
        }
        return false;
    }

    public List<CommentsDto> getCommentsByEpisodeId(String episodeId) {
        Episode episode = episodeRepository.findEpisodeByEpisodeId(episodeId);
        return commentRepository.findAllByEpisode(episode).stream()
                .map(commentMapper::mapToDto2)
                .toList();
    }
    public List<CommentsDto> getComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::mapToDto3)
                .toList();
    }
}
