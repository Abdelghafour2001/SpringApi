package com.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Episode {
  private String episodeId;
  private  String episodeNum;
  private String episodeUrl;
  private List<Comment> comments = new ArrayList<>();
  public void addComment(Comment comment) {
    comments.add(comment);
  }
}
