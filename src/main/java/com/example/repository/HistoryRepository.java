package com.example.repository;

import com.example.model.History;
import com.example.model.Post;
import com.example.model.Subreddit;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);
}