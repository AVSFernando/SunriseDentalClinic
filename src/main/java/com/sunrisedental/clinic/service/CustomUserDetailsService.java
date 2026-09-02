package com.sunrisedental.clinic.service;

import com.sunrisedental.clinic.entity.User;
import com.sunrisedental.clinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("LOGIN USERNAME = [" + username + "]");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("USER NOT FOUND!");
                    return new UsernameNotFoundException(
                            "User not found: " + username
                    );
                });

        System.out.println("USER FOUND = " + user.getUsername());
        System.out.println("PASSWORD FROM DB = [" + user.getPassword() + "]");
        System.out.println("ROLE FROM DB = [" + user.getRole() + "]");
        System.out.println("ACTIVE FROM DB = " + user.isActive());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .disabled(!user.isActive())
                .build();
    }
}