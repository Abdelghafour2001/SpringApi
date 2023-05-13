package com.example.controllers;

import com.example.model.Document;
import com.example.service.AnimeService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/anime"})
public class Controller {
  @Autowired
  AnimeService service;
  private RestTemplate restTemplate;

  public Controller() {
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

}