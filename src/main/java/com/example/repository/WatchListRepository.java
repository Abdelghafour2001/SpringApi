package com.example.repository;

import com.example.model.History;
import com.example.model.User;
import com.example.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WatchListRepository  extends JpaRepository<WatchList, Long> {
    List<WatchList> findByUser(User user);
}