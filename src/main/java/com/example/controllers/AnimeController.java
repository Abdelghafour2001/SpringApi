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

  //  @GetMapping
//  public List<Anime>list(){
//    return service.list();
//  }
  @GetMapping("/getRecentlyAdded")
  public ResponseEntity<Object> getRecentlyAdded() {
    String url = "http://localhost:3000/getRecentlyAdded";
    Object response = restTemplate.getForObject(url, Object.class);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getOngoingSeries")
  public ResponseEntity<Object> getOngoingSeries() {
    String url = "http://localhost:3000/getOngoingSeries";
    Object response = restTemplate.getForObject(url, Object.class);
    return ResponseEntity.ok(response);
  }

  //localhost:8080/anime/search?keyw=punch&page=1
  @GetMapping("/search")
  public ResponseEntity<Object> search(@RequestParam("keyw") String keyword,
                                       @RequestParam("page") int page) {
    try {

      String apiUrl = "http://localhost:3000/search?keyw=" + keyword + "&page=" + page;

      Object response = restTemplate.getForObject(apiUrl, Object.class);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Internal Error: " + e.getMessage());
    }
  }
  //http://localhost:3000/getEpisode/shingeki-no-kyojin-episode-5
  //http://localhost:8080/anime/watch-episode/spy-x-family-episode-1
  @GetMapping("/watch-episode/{id}")
  public ResponseEntity<Object> watchEpisode(@PathVariable("id") String id) {
    try {
      String apiUrl = "http://localhost:3000/getEpisode/" + id;
      Object response = restTemplate.getForObject(apiUrl, Object.class);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Internal Error: " + e.getMessage());
    }
  }
  // localhost:8080/anime/genre/Action?page=1
  @GetMapping("/genre/{genre}")
  public ResponseEntity<?> getGenrePage(@PathVariable("genre") String genre, @RequestParam int page) {
    try {
      String url = "http://localhost:3000/genre/" + genre + "?page=" + page;
      RestTemplate restTemplate = new RestTemplate();
      Object response = restTemplate.getForObject(url, Object.class);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Internal Error: " + e.getMessage());
    }
  }

  @GetMapping("/season/{season}")
  public ResponseEntity<?> getSeasonPage(@PathVariable("season") String season, @RequestParam(name="page", required = false) String page) {
    try {
      String url = "http://localhost:3000/season/" + season;
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
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

  @GetMapping("/anime-AZ-page")
  public ResponseEntity<Object> getAnimeAZPage(@RequestParam String aph, @RequestParam int page) {
    try {
      // create the URL for the API call
      String apiUrl = "https://localhost:3000/anime-AZ-page?aph=" + aph + "&page=" + page;

      // create a RestTemplate to make the API call
      RestTemplate restTemplate = new RestTemplate();

      // return the response from the API
      return restTemplate.getForEntity(apiUrl, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/anime-list-page")
  public ResponseEntity<Object> getAnimeList(@RequestParam(name = "page", required = false) String page) {
    try {
      String url = "http://localhost:3000/anime-list-page?page=" + page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/top-airing")
  public ResponseEntity<Object> getTopAiring(@RequestParam(name = "page", required = false) String page) {
    try {
      String url = "http://localhost:3000/top-airing?page=" + page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/animeList")
  public ResponseEntity<Object> getAnimeList(@RequestParam int page) {

    try {

      RestTemplate restTemplate = new RestTemplate();
      String url = "http://localhost:3000/animeList?page=" + page;
      ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
      List<Map<String, Object>> documents = (List<Map<String, Object>>) response.getBody();
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode node = objectMapper.createObjectNode();
      for (Map<String, Object> document : documents) {
        String liTitle = (String) document.get("liTitle");
        Document doc = new Document();
        doc.setImageUrl(extractImageUrl(liTitle));
        doc.setTitle(extractTitle(liTitle));
        doc.setGenres(extractGenres(liTitle));
        doc.setReleased(extractReleased(liTitle));
        doc.setStatus(extractStatus(liTitle));
        doc.setPlotSummary(extractPlotSummary(liTitle));
        node.set(doc.getTitle(), objectMapper.valueToTree(doc));
      }
      return ResponseEntity.ok(node);

    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  private static String extractTitle(String liTitle) {
    org.jsoup.nodes.Document doc = Jsoup.parse(liTitle);
    Element titleElement = doc.selectFirst("a.bigChar");
    return titleElement.text();
  }
  public static List<String> extractGenres(String html) {
    org.jsoup.nodes.Document doc = Jsoup.parse(html);
    Element genreElement = doc.selectFirst("p.type:contains(Genre)");
    String genreText = genreElement.text().replace("Genre: ", "");
    String[] genreArray = genreText.split(", ");
    return Arrays.asList(genreArray);
  }




  public int extractReleased(String html) {
    org.jsoup.nodes.Document doc = Jsoup.parse(html);
    Element releasedElement = doc.select("span:contains(Released)").first();
    if (releasedElement != null) {
      String released = releasedElement.parent().text().replace("Released:", "").trim();
      int releasedInt = Integer.parseInt(released);
      return releasedInt;
    }
    return -1;
  }

  private static String extractStatus(String liTitle) {
    org.jsoup.nodes.Document doc = Jsoup.parse(liTitle);
    Element statusElement = doc.selectFirst("p:contains(Status) span");
    String status = statusElement.text();
    return status;
  }


  public String extractPlotSummary(String html) {
    org.jsoup.nodes.Document doc = Jsoup.parse(html);
    Element summary = doc.select("p.sumer").first();
    String plotSummary = summary.text().replace("Plot Summary: ", "");
    return plotSummary;
  }


  private static String extractImageUrl(String liTitle) {
    org.jsoup.nodes.Document doc = Jsoup.parse(liTitle);
    Elements imgElements = doc.select("div.thumnail_tool img");
    if (imgElements.size() > 0) {
      return imgElements.get(0).attr("src");
    } else {
      return null;
    }
  }
  @GetMapping("/recent-release")
  public ResponseEntity<Object> getRecentRelease(@RequestParam(name = "type", required = false) String type,@RequestParam(name = "page", required = false) String page) {
    try {
      String url = "http://localhost:3000/recent-release?type=" + type+"?page="+page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/new-season")
  public ResponseEntity<Object> getNewSeason(@RequestParam(name = "page", required = false) String page) {
    try {
      String url = "http://localhost:3000/new-season?page=" + page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/ongoing-anime")
  public ResponseEntity<Object> getOngoing(@RequestParam(name = "page", required = false) String page) {
    try {
      String url = "http://localhost:3000/ongoing-anime?page=" + page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/completed-anime")
  public ResponseEntity<Object> getCompleted(@RequestParam(name = "page", required = false) String page) {
    try {
      String url = "http://localhost:3000/completed-anime?page=" + page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/popular-anime")
  public ResponseEntity<Object> getPopular(@RequestParam(name = "page", required = false) String page) {
    try {
      if(page==null){page="1";}
      String url = "http://localhost:3000/popular?page=" + page;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/getAnime/{id}")
  public ResponseEntity<Object> getAnime(@RequestParam(name = "id") String id) {
    try {
      String url = "http://localhost:3000/getAnime/" + id;
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    } catch (Exception e) {
      // handle any errors
      return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}