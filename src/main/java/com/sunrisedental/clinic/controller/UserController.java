package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.entity.User;
import com.sunrisedental.clinic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(
            @PathVariable String username) {

        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        if (userService.usernameExists(user.getUsername())) {
            return ResponseEntity.badRequest()
                    .body("Username already exists.");
        }

        User savedUser = userService.saveUser(user);

        return ResponseEntity.ok(savedUser);
    }
}