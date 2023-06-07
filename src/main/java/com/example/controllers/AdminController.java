package com.example.controllers;

import com.example.dto.MovieRequest;
import com.example.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    @PostMapping
    public ResponseEntity<Void> createMovie(@RequestBody MovieRequest movieRequest) {
        adminService.createMovie(movieRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        adminService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        adminService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("Comment deleted successfully");
    }
}
