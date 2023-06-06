package com.example.service;

import com.example.dto.CommentsDto;
import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.Subreddit;
import com.example.model.User;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import com.example.repository.SubredditRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    @Autowired
    private  PostRepository postRepository;
    private  UserRepository userRepository;
    private  CommentRepository commentRepository;
    private SubredditRepository subredditRepository;



    public AdminService() {

    }


    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    public Post getPostById(Long postId){
        return postRepository.findByPostId(postId);
    }
    public void deleteSubreddit(Long subredditId){
        subredditRepository.deleteById(subredditId);
    }
    public Subreddit getSubredditById(Long subredditId){
        return subredditRepository.findById(subredditId).get();
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void disableUser(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            User user1 = user.get();
            user1.setEnabled(false);
            userRepository.save(user1);
        }

    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    public List<Comment> getAllcomments(){return commentRepository.findAll();}

}
