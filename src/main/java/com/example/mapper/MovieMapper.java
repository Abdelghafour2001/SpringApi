package com.example.mapper;

import com.example.dto.MovieResponse;
import com.example.dto.PostResponse;
import com.example.model.Movie;
import com.example.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovieMapper {
    public MovieResponse mapToDto(Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        // movieResponse.setType(post.getType());
        
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setUsername(movie.getUser().getUsername());
        movieResponse.setImage(movie.getImage());
        movieResponse.setReleaseDate(movie.getReleaseDate());
        movieResponse.setUrl(movie.getUrl());
        movieResponse.setDescription(movie.getDescription());
        movieResponse.setDuration(movie.getDuration());
        return movieResponse;
    }
}
