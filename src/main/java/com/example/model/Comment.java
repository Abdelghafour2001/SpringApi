package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    private String id;
    private String text;
    private String author;
    private AtomicInteger likeCount = new AtomicInteger(0);
    private AtomicInteger disLikeCount = new AtomicInteger(0);

    public int likeCount() {
        return likeCount.get();
    }

    public int disLikeCount() {
        return disLikeCount.get();
    }
}