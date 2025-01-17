package com.projectM.controller;

import com.projectM.model.User;
import com.projectM.service.UserService;
import com.projectM.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        if (jwt != null && jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        User user = userServiceImpl.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(user);
    }
}
