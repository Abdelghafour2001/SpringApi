package com.example.controllers;


import com.example.model.Comment;
import com.example.model.Post;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;


    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Object> deletePost(@RequestParam("postId") Long postId) {
       adminService.deletePost(postId);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getPostById(@RequestParam("postId") Long postId){
        return ResponseEntity.ok(adminService.getPostById(postId));
    }

    @GetMapping("/post")
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(adminService.getAllPosts());
    }

    @DeleteMapping("/comment/{commentID}")
    public ResponseEntity<Object> deleteComment(@RequestParam("commentId") Long commentId){
        return ResponseEntity.ok(adminService.getCommentById(commentId));
    }
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Post> getCommentById(@RequestParam("commentId") Long commentId){
        return ResponseEntity.ok(adminService.getPostById(commentId));
    }

    @GetMapping("/comment")
    public ResponseEntity<List<Comment>> getAllComment(){
        return ResponseEntity.ok(adminService.getAllcomments());
    }



}
