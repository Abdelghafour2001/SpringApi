package com.example.service;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
public interface MovieService {
    ResponseEntity<Object> getMovies( String page, RestTemplate restTemplate);
    ResponseEntity<Object> getTvshows(String page, RestTemplate restTemplate);
    ResponseEntity<Object> getStreaming(String mediaId,String episodeId,String server,RestTemplate restTemplate);
    ResponseEntity<Object> getFiltered(String type,
                                       String quality,
                                       String released,
                                       String genre,
                                       String country,
                                       String page,
                                       RestTemplate restTemplate);
    ResponseEntity<Object> getEpisodeServers(String mediaId,String episodeId,RestTemplate restTemplate);
    ResponseEntity<Object> getMovieInfo(String mediaId,RestTemplate restTemplate);
    ResponseEntity<Object> getTopMoviesImdb(String type,String page,RestTemplate restTemplate);
    ResponseEntity<Object> getAllgenres(RestTemplate restTemplate);
    ResponseEntity<Object> getAllCountries(RestTemplate restTemplate);
    ResponseEntity<Object> getFilters(RestTemplate restTemplate);
    ResponseEntity<Object> getMovieByCountry(String country,String page, RestTemplate restTemplate);
    ResponseEntity<Object> getMovieByGenre(String genre,String page,RestTemplate restTemplate);
    ResponseEntity<Object> search(String query,String page, RestTemplate restTemplate);

    }
