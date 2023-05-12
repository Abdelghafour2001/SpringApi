package com.example.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QueryPropertiesDTO {

  private String animeId;
  private String type;
  private String animeTitle;
  private String animeImg;
  private String status;
  private String genres[];
  private String otherNames[];
  private String synopsis;
  private String totalEpisodes;
  private String sort;

}
