package com.example.library.core.use_cases.users;

import com.example.library.core.entities.User;

public interface UpdateUserUseCase {

    public User execute(Long id, User client);
}
