package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private String title;
    private String url;
    private String image;
    private String releaseDate;
    private String duration;
    private String type;
    private String description;
    private String username;
}
