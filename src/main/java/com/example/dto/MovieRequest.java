package com.example.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private String id;
    private String title;
    private String url;
    private String image;
    private String releaseDate;
    private String duration;
    private String type;
    private String description;

}
