package com.example.repository;

import com.example.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {
    Episode findEpisodeByEpisodeId(String episodeId);
    Episode deleteEpisodeByEpisodeId(String episodeId);
//    Episode findEpisodeById(String episodeId);
}
