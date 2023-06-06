package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Episode {
  @Id
  @Column(length = 55)
  private String episodeId;
  private  String episodeNum;
  private String episodeUrl;
  @OneToMany(mappedBy = "episode")
  private List<Comment> comments;


}
