package com.justibot.backend.service;

import com.justibot.backend.config.PasswordEncoderConfig;
import com.justibot.backend.model.User;
import com.justibot.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoderConfig passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }
        user.setPassword(PasswordEncoderConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);

    }
}

