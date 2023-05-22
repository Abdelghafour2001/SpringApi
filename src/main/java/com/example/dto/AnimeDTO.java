package com.example.dto;
import com.example.model.Episode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeDTO implements Serializable {
  private String animeId;
  private String type;
  private String animeTitle;
  private String animeImg;
  private String status;
  private String genres[];
  private String otherNames[];
  private String synopsis;
  private String totalEpisodes;
  private List<Episode> episodesList;
}
