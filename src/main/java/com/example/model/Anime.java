package com.example.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
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
  private String animeTitle;
  private String animeImg;
  private String status;
  private String genres[];
  private String otherNames[];
  private String synopsis;
  private String totalEpisodes;
 private List<GogoEpisode> episodesList;
}
