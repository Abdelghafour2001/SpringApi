package com.example.service;

import com.example.dto.AnimeDTO;
import com.example.model.Anime;
import com.example.model.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AnimeServiceImpl implements AnimeService{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<Object> getRecentlyAdded(String page,RestTemplate restTemplate) {
        if(page==null){page="1";}
        String url = "http://localhost:3000/getRecentlyAdded?page="+page;
        Object response = restTemplate.getForObject(url, Object.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> getOngoingSeries(String page,RestTemplate restTemplate) {
        if(page==null){page="1";}
        String url = "http://localhost:3000/getOngoingSeries?page="+page;
        Object response = restTemplate.getForObject(url, Object.class);
        return ResponseEntity.ok(response);    }

    @Override
    public ResponseEntity<Object> search(String keyword, String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            String apiUrl = "http://localhost:3000/search?keyw=" + keyword + "&page=" + page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> watchEpisode(String id,RestTemplate restTemplate) {
        try {
            String apiUrl = "http://localhost:3000/getEpisode/" + id;
            Object response = restTemplate.getForObject(apiUrl, Object.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getGenrePage(String genre, String page,RestTemplate restTemplate) {
        try {            if(page==null){page="1";}
            String url = "http://localhost:3000/genre/" + genre + "?page=" + page;
            Object response = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getSeasonPage(String season, String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
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

    @Override
    public ResponseEntity<Object> getAnimeAZPage(String aph, String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            String apiUrl = "https://localhost:3000/anime-AZ-page?aph=" + aph + "&page=" + page;
            return restTemplate.getForEntity(apiUrl, Object.class);
        } catch (Exception e) {
            // handle any errors
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAnimeListpage(String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            String url = "http://localhost:3000/anime-list-page?page=" + page;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        } catch (Exception e) {
            // handle any errors
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }    }

    @Override
    public ResponseEntity<Object> getTopAiring(String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
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

    @Override
    public ResponseEntity<Object> getAnimeList(String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
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
        }}
    @Override
    public ResponseEntity<Object> getRecentRelease(String type, String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            String url = "http://localhost:3000/recent-release?type=" + type+"?page="+page;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        } catch (Exception e) {
            // handle any errors
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }    }

    @Override
    public ResponseEntity<Object> getNewSeason(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            String url = "http://localhost:3000/new-season?page=" + page;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getOngoing(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
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

    @Override
    public ResponseEntity<Object> getCompleted(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
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

    @Override
    public ResponseEntity<Object> getPopular(String page,RestTemplate restTemplate) {
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

    @Override
    public ResponseEntity<Object> getAnime(String id,RestTemplate restTemplate) {
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







    /*@Override
    public List<Anime> list() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:3000/getOngoingSeries";
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Anime> persons = new ArrayList<>();
        try {
            List<AnimeDTO> dataObjects = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, AnimeDTO.class));
            for (AnimeDTO dataObject : dataObjects) {
                Anime person = new Anime();
                person.setAnimeId(dataObject.getAnimeId());
                mongoTemplate.insert(person);
                persons.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Anime person : persons) {
            System.out.println(person.getAnimeId());
        }
        return persons;
    }*/


}
