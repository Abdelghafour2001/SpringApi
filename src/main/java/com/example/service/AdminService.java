package com.example.service;

import com.example.dto.MovieRequest;
import com.example.model.Movie;
import com.example.model.User;
import com.example.repository.CommentRepository;
import com.example.repository.MovieRepository;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final AuthService authService;
    public AdminService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, MovieRepository movieRepository, AuthService authService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.authService = authService;
    }
    public void createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setDuration(movieRequest.getDuration());
        movie.setImage(movieRequest.getImage());
        movie.setTitle(movieRequest.getTitle());
        movie.setDescription(movieRequest.getDescription());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setUrl(movieRequest.getUrl());
        movie.setType(movie.getType());
        User user = authService.getCurrentUser();
        movie.setUser(user);
        movieRepository.save(movie);
    }
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
