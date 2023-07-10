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
import java.util.Optional;

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
    public boolean deleteWatchListItem(Long id) {
        Optional<WatchList> watchListItemOptional = watchRepository.findById(id);
        if (watchListItemOptional.isPresent()) {
            watchRepository.delete(watchListItemOptional.get());
            return true; // Item successfully deleted
        } else {
            return false; // Item with the specified ID doesn't exist
        }
    }

}