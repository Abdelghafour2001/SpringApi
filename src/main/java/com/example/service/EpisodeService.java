package com.example.service;

import com.example.model.Episode;
import com.example.repository.EpisodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    @Autowired
    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public Episode addEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    public void deleteEpisode(String episodeId) {
        episodeRepository.deleteEpisodeByEpisodeId(episodeId);
    }

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    public Episode getEpisodeById(String episodeId) {
        return episodeRepository.findEpisodeByEpisodeId(episodeId);
    }
}
