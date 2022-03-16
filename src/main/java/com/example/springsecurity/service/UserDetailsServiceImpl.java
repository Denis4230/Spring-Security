package com.example.springsecurity.service;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.security.GrantedAuthorityImpl;
import com.example.springsecurity.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buildUserDetails(userService.findByUserName(username));
    }

    private UserDetailsImpl buildUserDetails(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setGrantedAuthorities(buildAuthorities(user.getRoles()));
        return userDetails;
    }

    private Set<GrantedAuthority> buildAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new GrantedAuthorityImpl(role.getRole()))
                .collect(Collectors.toSet());
    }
}
