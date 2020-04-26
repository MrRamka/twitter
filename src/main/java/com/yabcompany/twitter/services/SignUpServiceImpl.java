package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.SignUpDto;
import com.yabcompany.twitter.models.Role;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saving user SignUpDto form
     *
     * @param form
     */
    @Override
    public void signUp(SignUpDto form) {
        User user = User
                .builder()
                .username(form.getUsername())
                .name(form.getName())
                .surname(form.getSurname())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }
}
