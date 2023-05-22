package com.example.service;

import com.example.model.Anime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface AnimeService {
        ResponseEntity<Object> getRecentlyAdded(String page, RestTemplate restTemplate);
        ResponseEntity<Object> getOngoingSeries(String page,RestTemplate restTemplate);
        ResponseEntity<Object> search(String keyword,
                                      String page,RestTemplate restTemplate);
        ResponseEntity<Object> watchEpisode(String id,RestTemplate restTemplate);
        ResponseEntity<?> getGenrePage(String genre,String page,RestTemplate restTemplate);
        ResponseEntity<?> getSeasonPage(String season,String page,RestTemplate restTemplate);
        ResponseEntity<Object> getAnimeAZPage( String aph, String page,RestTemplate restTemplate);
        ResponseEntity<Object> getTopAiring(String page,RestTemplate restTemplate);
        ResponseEntity<Object> getAnimeList( String page,RestTemplate restTemplate);
        ResponseEntity<Object> getRecentRelease(String type,String page,RestTemplate restTemplate);
        ResponseEntity<Object> getNewSeason(String page,RestTemplate restTemplate);
        ResponseEntity<Object> getOngoing(String page,RestTemplate restTemplate);
        ResponseEntity<Object> getCompleted(String page,RestTemplate restTemplate);
        ResponseEntity<Object> getPopular(String page,RestTemplate restTemplate);
        ResponseEntity<Object> getAnime(String id,RestTemplate restTemplate);


}
