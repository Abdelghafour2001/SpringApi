package com.example.repository;

import com.example.model.History;
import com.example.model.Movie;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByUser(User user);
Movie findByTitle(String id);
}
