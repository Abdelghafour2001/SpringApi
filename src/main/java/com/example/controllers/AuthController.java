package com.example.controllers;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RefreshTokenRequest;
import com.example.dto.RegisterRequest;
import com.example.model.History;
import com.example.model.User;
import com.example.model.WatchList;
import com.example.repository.UserRepository;
import com.example.repository.WatchListRepository;
import com.example.service.AuthService;
import com.example.service.HistoryService;
import com.example.service.RefreshTokenService;
import com.example.service.WatchListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final HistoryService historyService;
    private final WatchListService watchListService;
    private final WatchListRepository watchRepository;
    private final UserRepository userRepository;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }
    @GetMapping("getAccountData/{token}")
    public ResponseEntity<Object> getAccountData(@PathVariable String token) {
        User user=authService.getAccountData(token);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/history")
    public ResponseEntity<String> addToHistory(@Valid @RequestBody History history) {
        history.setCreatedDate(Instant.now());
        historyService.saveHistory(history);
        return ResponseEntity.status(OK).body("Added to history Successfully!!");
    }
    @GetMapping(value = "/history",params = "username")
    public ResponseEntity<List<History>> getHistory(@RequestParam String username) {
        return status(HttpStatus.OK).body(historyService.getHistoryByUser(username));
    }
    @PostMapping("/watchList")
    public ResponseEntity<String> addToWatchList(@Valid @RequestBody WatchList watchList) {
        String username= watchList.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        watchList.setUser(user);
        watchListService.saveWatchList(watchList);

        return ResponseEntity.status(OK).body("Added to Watchlist Successfully!!");
    }
    @GetMapping(value = "/userWatchList",params = "username")
    public ResponseEntity<List<WatchList>> getWatchList(@RequestParam String username) {
        return status(HttpStatus.OK).body(watchListService.getWatchListByUser(username));
    }
    @DeleteMapping(value = "/userWatchList/{id}")
    public ResponseEntity<String> deleteWatchListItem(@PathVariable Long id) {
        boolean deleted = watchListService.deleteWatchListItem(id);
        if (deleted) {
            return ResponseEntity.ok("Watchlist item deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
