package com.projectM.controller;

import com.projectM.config.JwtProvider;
import com.projectM.model.User;
import com.projectM.repository.UserRepository;
import com.projectM.request.LoginRequest;
import com.projectM.response.AuthResponse;
import com.projectM.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //Sign-up controller
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUpHandler(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        User userExists = userRepository.findByEmail(email);

        if (userExists != null) {
            throw new Exception("email already exists!");
        }else {

            User createdUser = new User();
            String pwd = user.getPassword();
            String hashedPwd = passwordEncoder.encode(pwd);

            createdUser.setPassword(hashedPwd);
            createdUser.setEmail(email);
            createdUser.setFull_name(user.getFull_name());
            userRepository.save(createdUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generateToken(authentication);

            AuthResponse res = new AuthResponse();
            res.setMessage("User signed up successfully!");
            res.setJwt(jwt);

            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
    }

    //Sign-in controller
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) {
        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Logged in successfully!");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        //Check if the user exists
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        //Check if the password matches
        boolean passwordMatches = passwordEncoder.matches(password,userDetails.getPassword());
        if(!passwordMatches){
            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null);

    }
}
