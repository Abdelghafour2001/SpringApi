package com.example.controllers;

import com.example.model.Document;
import com.example.service.AnimeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@RestController
@RequestMapping({"/anime"})
public class AnimeController {
  @Autowired
  AnimeService service;
  private RestTemplate restTemplate;

  public AnimeController() {
    restTemplate = new RestTemplate();
  }

  @GetMapping("/getRecentlyAdded")
  public ResponseEntity<Object> getRecentlyAdded(@RequestParam("page") String page) {
return service.getRecentlyAdded(page,restTemplate);
  }

  @GetMapping("/getOngoingSeries")
  public ResponseEntity<Object> getOngoingSeries(@RequestParam("page") String page) {
    return service.getOngoingSeries(page,restTemplate);
  }

  //localhost:8080/anime/search?keyw=punch&page=1
  @GetMapping("/search")
  public ResponseEntity<Object> search(@RequestParam("keyw") String keyword,
                                       @RequestParam("page") String page) {
  return service.search(keyword,page,restTemplate);
  }
  //http://localhost:3000/getEpisode/shingeki-no-kyojin-episode-5
  //http://localhost:8080/anime/watch-episode/spy-x-family-episode-1
  @GetMapping("/watch-episode/{id}")
  public ResponseEntity<Object> watchEpisode(@PathVariable("id") String id) {
    return service.watchEpisode(id,restTemplate);
  }
  // localhost:8080/anime/genre/Action?page=1
  @GetMapping("/genre/{genre}")
  public ResponseEntity<?> getGenrePage(@PathVariable("genre") String genre, @RequestParam String page) {
return service.getGenrePage(genre,page,restTemplate);
  }

  @GetMapping("/season/{season}")
  public ResponseEntity<?> getSeasonPage(@PathVariable("season") String season, @RequestParam(name="page", required = false) String page) {
return service.getSeasonPage(season,page,restTemplate);
  }

  @GetMapping("/anime-AZ-page")
  public ResponseEntity<Object> getAnimeAZPage(@RequestParam String aph, @RequestParam String page) {
    return service.getAnimeAZPage(aph,page,restTemplate);
  }

  @GetMapping("/anime-list-page")
  public ResponseEntity<Object> getAnimeListpage(@RequestParam(name = "page", required = false) String page) {
return service.getAnimeListpage(page,restTemplate);
  }
  @GetMapping("/top-airing")
  public ResponseEntity<Object> getTopAiring(@RequestParam(name = "page", required = false) String page) {
return service.getTopAiring(page,restTemplate);
  }
  @GetMapping("/animeList")
  public ResponseEntity<Object> getAnimeList(@RequestParam String page) {
    return service.getAnimeList(page,restTemplate);
  }
  @GetMapping("/recent-release")
  public ResponseEntity<Object> getRecentRelease(@RequestParam(name = "type", required = false) String type,@RequestParam(name = "page", required = false) String page) {
  return service.getRecentRelease(type,page,restTemplate);
  }
  @GetMapping("/new-season")
  public ResponseEntity<Object> getNewSeason(@RequestParam(name = "page", required = false) String page) {
    return service.getNewSeason(page,restTemplate);
  }
  @GetMapping("/ongoing-anime")
  public ResponseEntity<Object> getOngoing(@RequestParam(name = "page", required = false) String page) {
      return service.getOngoing(page,restTemplate);
  }
  @GetMapping("/completed-anime")
  public ResponseEntity<Object> getCompleted(@RequestParam(name = "page", required = false) String page) {
    return service.getCompleted(page,restTemplate);
  }
  @GetMapping("/popular-anime")
  public ResponseEntity<Object> getPopular(@RequestParam(name = "page", required = false) String page) {
 return service.getPopular(page,restTemplate);
  }
  @GetMapping("/getAnime/{id}")
  public ResponseEntity<Object> getAnime(@RequestParam(name = "id") String id) {
      return service.getAnime(id,restTemplate);
  }

}