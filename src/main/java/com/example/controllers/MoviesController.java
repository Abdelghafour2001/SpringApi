package com.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping({"/movies"})
public class MoviesController {
    private RestTemplate restTemplate;

    public MoviesController() {
        restTemplate = new RestTemplate();
    }
    //http://localhost:8080/movies/getRecentlyAdded?page=3
    @GetMapping("/getMovies")
    public ResponseEntity<Object> getMovies(@RequestParam(name = "page", required = false) int page) {
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
    public ResponseEntity<Object> getTvshows(@RequestParam(name = "page", required = false) int page) {
        try {
            String apiUrl = "http://localhost:3030/api/tv-shows?page=" + page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    //http://localhost:8080/movies/getStreaming?mediaId=watch-the-best-man-96607&episodeId=96607&server=UpCloud
    @GetMapping("/getStreaming")
    public ResponseEntity<Object> getStreaming(@RequestParam(name = "mediaId") String mediaId,@RequestParam(name = "episodeId") String episodeId,@RequestParam(name = "server") String server) {
        try {
            String apiUrl = "http://localhost:3030/api/streaming?mediaId=" + mediaId+"&episodeId="+episodeId+"&server="+server;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/getFiltered")
    public ResponseEntity<Object> getFiltered(@RequestParam(name = "type", required = false) String type,
                                              @RequestParam(name = "quality", required = false) String quality,
                                              @RequestParam(name = "released", required = false) String released,
                                              @RequestParam(name = "genre", required = false) String genre,
                                              @RequestParam(name = "country", required = false) String country,
                                              @RequestParam(name = "page", required = false) String page){

        try {
            //String apiUrl = "http://localhost:3030/api/filter?type=" + type+"&quality="+quality+"&released="+released+"&genre="+genre+"&country="+country+"&page="+page;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/filter");
            if (type != null) {
                builder.queryParam("type", type);
            }
            if (quality != null) {
                builder.queryParam("quality", quality);
            }
            if (released != null) {
                builder.queryParam("released", released);
            }
            if (genre != null) {
                builder.queryParam("genre", genre);
            }
            if (country != null) {
                builder.queryParam("country", country);
            }

            if (page != null) {
                builder.queryParam("page", page);
            }
            // Get the final API URL
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/getEpisodeServers")
    public ResponseEntity<Object> getEpisodeServers(@RequestParam(name = "mediaId") String mediaId,@RequestParam(name = "episodeId") String episodeId) {
        try {
            String apiUrl = "http://localhost:3030/api/episode-servers?mediaId=" + mediaId+"&episodeId="+episodeId;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/getMovieInfo")
    public ResponseEntity<Object> getMovieInfo(@RequestParam(name = "mediaId") String mediaId) {
        try {
            String apiUrl = "http://localhost:3030/api/info?mediaId=" + mediaId;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/top-imdb")
    public ResponseEntity<Object> getTopMoviesImdb(@RequestParam(name = "type",required = false) String type,@RequestParam(name = "page",required = false) String page) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/filter");
            if (type != null) {
                builder.queryParam("type", type);
            }
            if (page != null) {
                builder.queryParam("page", page);
            }
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/all-genres")
    public ResponseEntity<Object> getAllgenres(){
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/all-genres");
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/all-countries")
    public ResponseEntity<Object> getAllCountries(){
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/all-countries");
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/all-filters")
    public ResponseEntity<Object> getFilters(){
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/filters-list");
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/country/{country}")
    public ResponseEntity<Object> getMovieByCountry(@PathVariable("country") String country,@RequestParam(name = "page",required = false) String page){
        try {

            String apiUrl = "http://localhost:3030/api/country/" + country+"?page="+page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getMovieByGenre(@PathVariable("genre") String genre,@RequestParam(name = "page",required = false) String page){
        try {
            String apiUrl = "http://localhost:3030/api/genre/" + genre+"?page="+page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<Object> search(@PathVariable("query") String query,@RequestParam(name = "page",required = false) String page){
        try {
            String apiUrl = "http://localhost:3030/api/search/" + query+"?page="+page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }
}
