package com.example.library.services;

import com.example.library.domain.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User createUser(User user) {
        return repository.save(user);
    }

    public boolean checkUserParameters(User user) {
        return !repository.existsByEmail(user.getEmail());
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Optional<User> updateUser(Long id, User user) {
        Optional<User> userById = repository.findById(id);
        if (userById.isPresent()) {
            User userFound = userById.get();
            userFound.setName(user.getName());
            userFound.setEmail(user.getEmail());
            userFound.setPhone(user.getPhone());
            userFound.setCreatedAt(user.getCreatedAt());
            return Optional.of(repository.save(userFound));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteUser(Long id) {
        Optional<User> userById = repository.findById(id);
        if (userById.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
