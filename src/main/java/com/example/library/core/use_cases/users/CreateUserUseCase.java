package com.example.library.core.use_cases.users;

import com.example.library.core.entities.User;

public interface CreateUserUseCase {

    public User execute(User client);
}
