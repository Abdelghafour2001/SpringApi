package com.example.controllers;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping("/getUsers")
    public List<User> getUsers(@RequestParam(required = false) String username) {
        if (username == null) {
            return userDetailsService.getAll();
        } else {
            //return userDetailsService.findByUsername(username).stream().toList();
            return userDetailsService.getAll();

        }
    }

   @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(userDetailsService.save(user));
   }

 @GetMapping("/getUserById/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User dummy = new User();
        dummy.setUserId(id);
        return userDetailsService.getById(dummy);
    }

       @PutMapping("/updateUser/{id}")
          public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
          UserDto userdto = userDetailsService.getById(updatedUser);
          User user = new User();
          user.setUsername(updatedUser.getUsername());
          user.setPassword(updatedUser.getPassword());
          user.setEmail(updatedUser.getEmail());
          return userDetailsService.save(user);
      }
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        User dummy = new User();
        dummy.setUserId(id);
       userDetailsService.deleteById(dummy);
    }
}

