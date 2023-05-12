package com.example.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Anime {
  private String animeId;
  private String type;
  private String animeTitle;
  private String animeImg;
  private String status;
  private String genres[];
  private String otherNames[];
  private String synopsis;
  private String totalEpisodes;
 private List<GogoEpisode> episodesList;
}
