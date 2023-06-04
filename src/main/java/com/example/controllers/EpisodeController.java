package com.example.controllers;

import com.example.model.Episode;
import com.example.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/episodes")
public class EpisodeController {
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping
    public ResponseEntity<Episode> addEpisode(@RequestBody Episode episode) {
        Episode addedEpisode = episodeService.addEpisode(episode);
        return new ResponseEntity<>(addedEpisode, HttpStatus.CREATED);
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable String episodeId) {
        episodeService.deleteEpisode(episodeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Episode>> getAllEpisodes() {
        List<Episode> episodes = episodeService.getAllEpisodes();
        return ResponseEntity.ok(episodes);
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable String episodeId) {
        Optional<Episode> episode = Optional.ofNullable(episodeService.getEpisodeById(episodeId));
        return episode.map(ResponseEntity::ok).orElseGet(ResponseEntity.notFound()::build);
    }

}
