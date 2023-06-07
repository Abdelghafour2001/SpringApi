package com.example.controllers;

import com.example.dto.CommentsDto;
import com.example.model.Comment;
import com.example.model.Episode;
import com.example.repository.EpisodeRepository;
import com.example.service.AnimeService;
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



    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping(params = "postId")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping(params = "username")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@RequestParam String username){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(username));
    }
    @GetMapping(params = "episodeId")
    public ResponseEntity<List<CommentsDto>> getCommentsByEpisode(@RequestParam String episodeId) {
        return ResponseEntity.status(OK)
                .body(commentService.getCommentsByEpisodeId(episodeId));
    }

}
