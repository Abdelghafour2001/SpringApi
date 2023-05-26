package com.example.service;

import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.mapper.PostMapper;
import com.example.model.History;
import com.example.model.Post;
import com.example.model.Subreddit;
import com.example.model.User;
import com.example.repository.HistoryRepository;
import com.example.repository.PostRepository;
import com.example.repository.SubredditRepository;
import com.example.repository.UserRepository;
import com.example.util.PostNotFoundException;
import com.example.util.SubredditNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional

public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    public List<History> getHistoryByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return historyRepository.findByUser(user);
    }

    public History saveHistory(History history) {
        return historyRepository.save(history);
    }
    public void deleteHistory(History history) {
        historyRepository.delete(history);
    }
}
