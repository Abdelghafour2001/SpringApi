package com.example.controllers;

import com.example.service.AnimeService;
import com.example.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping({"/movies"})
@AllArgsConstructor

public class MoviesController {
    private RestTemplate restTemplate;
    @Autowired
    MovieService service;
    public MoviesController() {
        restTemplate = new RestTemplate();
    }
    //http://localhost:8080/movies/getRecentlyAdded?page=3
    @GetMapping("/getMovies")
    public ResponseEntity<Object> getMovies(@RequestParam(name = "page", required = false) String page) {
        return service.getMovies(page,restTemplate);
    }
    @GetMapping("/getTvshows")
    public ResponseEntity<Object> getTvshows(@RequestParam(name = "page", required = false) String page) {
        return service.getTvshows(page,restTemplate);
    }
    //http://localhost:8080/movies/getStreaming?mediaId=watch-the-best-man-96607&episodeId=96607&server=UpCloud
    @GetMapping("/getStreaming")
    public ResponseEntity<Object> getStreaming(@RequestParam(name = "mediaId") String mediaId,
                                               @RequestParam(name = "episodeId") String episodeId,
                                               @RequestParam(name = "server") String server) {
        return service.getStreaming(mediaId,episodeId,server,restTemplate);
    }
    @GetMapping("/getFiltered")
    public ResponseEntity<Object> getFiltered(@RequestParam(name = "type", required = false) String type,
                                              @RequestParam(name = "quality", required = false) String quality,
                                              @RequestParam(name = "released", required = false) String released,
                                              @RequestParam(name = "genre", required = false) String genre,
                                              @RequestParam(name = "country", required = false) String country,
                                              @RequestParam(name = "page", required = false) String page){

        return  service.getFiltered(type,quality,released,genre,country,page,restTemplate);
    }
    @GetMapping("/getEpisodeServers")
    public ResponseEntity<Object> getEpisodeServers(@RequestParam(name = "mediaId") String mediaId,
                                                    @RequestParam(name = "episodeId") String episodeId) {
        return service.getEpisodeServers(mediaId,episodeId,restTemplate);
    }
    @GetMapping("/getMovieInfo")
    public ResponseEntity<Object> getMovieInfo(@RequestParam(name = "mediaId") String mediaId) {
        return service.getMovieInfo(mediaId,restTemplate);
    }
    @GetMapping("/top-imdb")
    public ResponseEntity<Object> getTopMoviesImdb(@RequestParam(name = "type",required = false) String type,@RequestParam(name = "page",required = false) String page) {
    return service.getTopMoviesImdb(type,page,restTemplate);
    }
    @GetMapping("/all-genres")
    public ResponseEntity<Object> getAllgenres(){
return service.getAllgenres(restTemplate);
    }
    @GetMapping("/all-countries")
    public ResponseEntity<Object> getAllCountries(){
    return service.getAllCountries(restTemplate);
    }
    @GetMapping("/all-filters")
    public ResponseEntity<Object> getFilters(){
        return service.getFilters(restTemplate);
    }
    @GetMapping("/country/{country}")
    public ResponseEntity<Object> getMovieByCountry(@PathVariable("country") String country,@RequestParam(name = "page",required = false) String page){
    return service.getMovieByCountry(country,page,restTemplate);
    }
    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getMovieByGenre(@PathVariable("genre") String genre,@RequestParam(name = "page",required = false) String page){
    return service.getMovieByGenre(genre,page,restTemplate);
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<Object> search(@PathVariable("query") String query,@RequestParam(name = "page",required = false) String page){
        return service.search(query,page,restTemplate);
    }
}
