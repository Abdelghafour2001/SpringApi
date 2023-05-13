package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {
    private String id;
    private String title;
    private String url;
    private String image;
    private String releaseDate;
    private String duration;
    private String type;
    private String seasons;
    private String lastEpisodes;
}
