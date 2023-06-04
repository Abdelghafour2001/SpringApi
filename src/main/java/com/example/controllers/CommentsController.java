package com.example.controllers;

import com.example.dto.CommentsDto;
import com.example.model.Comment;
import com.example.model.Episode;
import com.example.repository.EpisodeRepository;
import com.example.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final EpisodeRepository episodeRepository;


    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping(params = "postId")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping(params = "userName")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@RequestParam String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }
    @GetMapping(params = "episodeId")
    public List<CommentsDto> getCommentsByEpisode(@PathVariable String episodeId) {

        Episode episode = episodeRepository.findEpisodeByEpisodeId(episodeId);
        return commentService.getCommentsByEpisodeId(episode);
    }

}
