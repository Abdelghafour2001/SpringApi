package com.example.service;

import com.example.model.History;
import com.example.model.User;
import com.example.model.WatchList;
import com.example.repository.HistoryRepository;
import com.example.repository.UserRepository;
import com.example.repository.WatchListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
@Transactional

public class WatchListService {
    private final WatchListRepository watchRepository;
    private final UserRepository userRepository;

    public List<WatchList> getWatchListByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return watchRepository.findByUser(user);
    }

    public WatchList saveWatchList(WatchList watchList) {

        return watchRepository.save(watchList);
    }
    public void deleteWatchList(WatchList watchList) {
        watchRepository.delete(watchList);
    }
}