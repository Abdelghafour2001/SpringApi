package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Episode {
  @Id
  private String episodeId;
  private  String episodeNum;
  private String episodeUrl;
  @OneToMany(mappedBy = "episode")
  private List<Comment> comments;


}
