package com.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class Document {
    private String title;
    private String imageUrl;
    private List<String> genres;
    private int released;
    private String status;
    private String plotSummary;
}