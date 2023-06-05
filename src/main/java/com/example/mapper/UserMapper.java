package com.example.mapper;

import com.example.dto.SubredditDto;
import com.example.dto.UserDto;
import com.example.model.Subreddit;
import com.example.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
@Component
public class UserMapper {
    public UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setCreated(user.getCreated().toString());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
