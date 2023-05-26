package com.example.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.model.*;
import com.example.repository.CommentRepository;
import com.example.repository.VoteRepository;
import com.example.service.AuthService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static com.example.model.VoteType.DOWNVOTE;
import static com.example.model.VoteType.UPVOTE;

@Component
@AllArgsConstructor
public class PostMapper {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    public Post map(PostRequest postRequest, Subreddit subreddit, User user) {
        Post post = new Post();
        post.setCreatedDate(Instant.now());
        post.setDescription(postRequest.getDescription());
        post.setSubreddit(subreddit);
        post.setVoteCount(0);
        post.setUser(user);
        return post;
    }

    public PostResponse mapToDto(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getPostId());
        postResponse.setSubredditName(post.getSubreddit().getName());
        postResponse.setUserName(post.getUser().getUsername());
        postResponse.setCommentCount(commentCount(post));
        postResponse.setDuration(getDuration(post));
        postResponse.setUpVote(isPostUpVoted(post));
        postResponse.setDownVote(isPostDownVoted(post));
        return postResponse;
    }

    private Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    private String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    private boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    private boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(
                    post,
                    authService.getCurrentUser()
            );
            return voteForPostByUser
                    .filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
