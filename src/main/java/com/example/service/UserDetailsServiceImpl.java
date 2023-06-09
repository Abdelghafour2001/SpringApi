package com.example.service;

import com.example.dto.UserDto;
import com.example.mapper.SubredditMapper;
import com.example.mapper.UserMapper;
import com.example.model.Subreddit;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.util.SpringRedditException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
    @Transactional(readOnly = true)
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }
    public User save(User user) {

       return userRepository.save(user);
    }
    public void deleteById(User user) {

        userRepository.deleteById(user.getUserId());
    }
    public UserDto getById(User user){
        User userOptional = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + user.getUsername()));
        return userMapper.mapUserToDto(userOptional);

    }
}
