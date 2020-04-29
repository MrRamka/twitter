package com.yabcompany.twitter.services;

import com.yabcompany.twitter.dto.SignUpDto;
import com.yabcompany.twitter.dto.UserDto;
import com.yabcompany.twitter.models.Role;
import com.yabcompany.twitter.models.User;
import com.yabcompany.twitter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Get users using pagination and sorting type
     *
     * @param page     page number
     * @param size     amount users on page
     * @param property sorting type
     * @return List of UserDto
     * @see UserDto
     */

    @Override
    public List<UserDto> getAllUsers(Integer page, Integer size, String property) {
        Sort sort = Sort.by(property);
        PageRequest request = PageRequest.of(page, size, sort);
        Page<User> pageResult = userRepository.findAll(request);
        List<User> users = pageResult.getContent();
        return UserDto.from(users);
    }

    /**
     * Get all users
     *
     * @return List of UserDto
     * @see UserDto
     */

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserDto.from(users);
    }


    /**
     * Get single user using id
     *
     * @param userId
     * @return UserDto
     * @see UserDto
     */
    @Override
    public UserDto getUser(Long userId) {
        return UserDto.from(userRepository.getOne(userId));
    }


    /**
     * Save single user from SignUpDto
     *
     * @param userData
     * @return UserDto
     * @see UserDto
     * @see SignUpDto
     */
    @Override
    public UserDto addUser(SignUpDto userData) {
        User user = User
                .builder()
                .username(userData.getUsername())
                .name(userData.getName())
                .surname(userData.getSurname())
                .hashPassword(userData.getPassword())
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Cant find User");
    }


    @Override
    public void addFollower(String sourceUserUsername, String targetUserUsername) {
        User target = getUserByUsername(targetUserUsername);
        User source = getUserByUsername(sourceUserUsername);
        target.getFollowing().add(source);
        userRepository.save(target);
    }


    @Override
    public Set<User> getFollowsUsers(User user) {
        Optional<User> curUser = userRepository.findUserByUsername(user.getUsername());

        if (curUser.isPresent()) {
            return curUser.get().getFollowing();
        }
        throw new UsernameNotFoundException("Cant Find user");

    }
}
