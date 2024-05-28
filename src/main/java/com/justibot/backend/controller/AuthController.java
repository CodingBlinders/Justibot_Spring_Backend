package com.justibot.backend.controller;

import com.justibot.backend.model.JwtResponse;
import com.justibot.backend.model.User;
import com.justibot.backend.service.UserService;
import com.justibot.backend.util.JwtHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request, HttpServletResponse response) {
        doAuthenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtHelper.generateToken(userDetails);

        // Create a cookie and set the JWT token
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // Add the cookie to the response
        response.addCookie(cookie);

        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


    private void doAuthenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password!!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password!!");
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/user")
    public String getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return "Authenticated user: " + userDetails.getUsername();
    }

    @GetMapping("/health")
    public String health() {
        return "Server Running!";
    }
}
