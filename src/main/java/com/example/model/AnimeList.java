package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AnimeList {
  private String animeId;
  private String animeTitle;
  private String animeUrl;
  private String animeImg;
private String status;
}
