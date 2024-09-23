package com.example.library.infrastructure.gateways;

import com.example.library.core.entities.User;
import com.example.library.core.gateways.UserGateway;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryGateway implements UserGateway {
    private final UserRepository repository;
    private final UserEntityMapper mapper;

    @Override
    public User createUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity newUser = repository.save(entity);
        return mapper.toUser(newUser);
    }

    @Override
    public User saveUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity userUpdated = repository.saveAndFlush(entity);
        return mapper.toUser(userUpdated);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<UserEntity> user = repository.findById(id);
        if (user.isPresent()) {
            repository.delete(user.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> getUser(Long id) {
        Optional<UserEntity> entity = repository.findUserById(id);
        return entity.map(mapper::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entity = repository.findClientByEmail(email);
        return entity.map(mapper::toUser);
    }

    @Override
    public List<User> getAllUsers() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toUser)
                .collect(Collectors.toList());
    }
}
