package com.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {


    private Long userId;
    private String username;
    private String password;
    private String email;
    private Instant created;
    private boolean enabled;


}