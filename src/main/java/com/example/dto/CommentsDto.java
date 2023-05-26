package com.example.dto;

import com.example.model.Post;
import com.example.model.User;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentsDto {
    private Long id;
    private Long postId;
    private Instant createdDate;
    @NotBlank
    private String text;
    private String userName;
    private User user;
private Post post;
}
