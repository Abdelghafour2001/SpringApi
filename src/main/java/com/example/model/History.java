package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    private String name;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private String anime_id;
   private String type;
   private String released;
    private String animeTitle;
    private String dubOrSub;
    private Instant createdDate;
}
