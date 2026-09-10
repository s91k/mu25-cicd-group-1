package com.example.libraryAPI.service;

import com.example.libraryAPI.dto.UserRequest;
import com.example.libraryAPI.exception.ResourceNotFoundException;
import com.example.libraryAPI.model.User;
import com.example.libraryAPI.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserRequest request) {
        validate(request);
        User user = new User(request.getFirstName(), request.getLastName());
        return userRepository.save(user);
    }

    public List<User> findAll() {

        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User update(Long id, UserRequest request) {
        validate(request);
        User user = findById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    private void validate(UserRequest request) {
        if (request.getFirstName() == null || request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("firstName must not be empty");
        }
        if (request.getLastName() == null || request.getLastName().isBlank()) {
            throw new IllegalArgumentException("lastName must not be empty");
        }
    }
}