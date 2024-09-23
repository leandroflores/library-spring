package com.example.library.core.use_cases.users;

import com.example.library.core.entities.User;

import java.util.List;

public interface GetAllUsersUseCase {

    public List<User> execute();
}
