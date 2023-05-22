package com.example.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "persons")
public class Anime {
  @Id
  private String animeId;
  private String type;
  private String animeUrl;
  private String releasedDate;
  private String animeTitle;
  private String animeImg;
  private String status;
  private ArrayList<String>genres;
  private String otherNames;
  private String synopsis;
  private String episodeNum;
  private String episodeId;
  private String subOrDub;
  private String totalEpisodes;
 private List<Episode> episodesList;
}
