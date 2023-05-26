package com.example.mapper;

import com.example.dto.SubredditDto;
import com.example.model.Post;
import com.example.model.Subreddit;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class SubredditMapper {

    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        SubredditDto subredditDto = new SubredditDto();
        subredditDto.setName(subreddit.getName());
        subredditDto.setDescription(subreddit.getDescription());
        subredditDto.setNumberOfPosts(subreddit.getPosts().size());
        return subredditDto;
    }

    public Subreddit mapDtoToSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = new Subreddit();
        subreddit.setId(subredditDto.getId());
        subreddit.setCreatedDate(Instant.now());
        subreddit.setName(subredditDto.getName());
        subreddit.setDescription(subredditDto.getDescription());
        // Set other properties of Subreddit if needed
        return subreddit;
    }
}