package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.SignUpDto;
import com.yabcompany.twitter.dto.UserDto;
import com.yabcompany.twitter.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<UserDto> getAllUsers(Integer page, Integer size, String sort);

    List<UserDto> getAllUsers();

    UserDto getUser(Long userId);

    UserDto addUser(SignUpDto userData);

    User getUserByUsername(String username);

    void addFollower(String sourceUserUsername, String targetUserUsername);

    Set<User> getFollowsUsers(User user);

}
