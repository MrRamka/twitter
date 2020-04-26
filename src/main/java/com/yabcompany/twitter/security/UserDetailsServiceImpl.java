package com.yabcompany.twitter.security;

import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserDetailsImpl(user);
        }
        throw new UsernameNotFoundException("User not found");
    }
}