package com.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/movies"})
public class MoviesController {
    private RestTemplate restTemplate;

    public MoviesController() {
        restTemplate = new RestTemplate();
    }
    //http://localhost:8080/movies/getRecentlyAdded?page=3
    @GetMapping("/getMovies")
    public ResponseEntity<Object> getMovies(@RequestParam("page") int page) {
        try {
            String apiUrl = "http://localhost:3030/api/movies?page=" + page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/getTvshows")
    public ResponseEntity<Object> getTvshows(@RequestParam("page") int page) {
        try {
            String apiUrl = "http://localhost:3030/api/tv-shows?page=" + page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
}
