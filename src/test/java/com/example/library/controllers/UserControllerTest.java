package com.example.library.controllers;

import com.example.library.TestConfig;
import com.example.library.domain.User;
import com.example.library.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> users = TestConfig.users();
        User user = users.get(0);

        when(service.getAllUsers()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value(user.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdAt").value(user.createdAtFormatted()));
    }

    @Test
    public void testGetUser() throws Exception {
        User user = TestConfig.user();

        when(service.getUserById(user.getId())).thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value(user.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("createdAt").value(user.createdAtFormatted()));
    }

    @Test
    public void testGetNotFoundUser() throws Exception {
        User user = TestConfig.user();

        when(service.getUserById(user.getId())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        User user = TestConfig.user();
        String json = TestConfig.userJson();

        when(service.checkUserParameters(user)).thenReturn(true);
        when(service.createUser(user)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/users/")
           .contentType(MediaType.APPLICATION_JSON)
           .content(json))
           .andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    public void testCreateUserWithNullName() throws Exception {
        User user = TestConfig.user().clone();
             user.setName(null);
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.checkUserParameters(user)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Name is required"));
    }

    @Test
    public void testCreateUserWithEmptyName() throws Exception {
        User user = TestConfig.user().clone();
             user.setName("");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.checkUserParameters(user)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Name can not be empty"));
    }

    @Test
    public void testCreateUserWithRepeatedEmail() throws Exception {
        User user = TestConfig.user();
        String json = TestConfig.userJson();

        when(service.checkUserParameters(user)).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.post("/users/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content().string("User email already exists"));
    }

    @Test
    public void testCreateUserWithNullPhone() throws Exception {
        User user = TestConfig.user().clone();
             user.setPhone(null);
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.checkUserParameters(user)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Phone is required"));
    }

    @Test
    public void testCreateUserWithEmptyPhone() throws Exception {
        User user = TestConfig.user().clone();
             user.setPhone("");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.checkUserParameters(user)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Phone can not be empty"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = TestConfig.user();

        when(service.deleteUser(user.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

    @Test
    public void testDeleteNotFoundUser() throws Exception {
        User user = TestConfig.user();

        when(service.deleteUser(user.getId())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = TestConfig.user();
             user.setName("Greg");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value(user.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value(user.getPhone()));
    }

    @Test
    public void testUpdateNotFoundUser() throws Exception {
        User user = TestConfig.user();
             user.setName("Greg");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));;
    }

    @Test
    public void testUpdateUserWithEmptyName() throws Exception {
        User user = TestConfig.user();
             user.setName("");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Name can not be empty"));;
    }

    @Test
    public void testUpdateUserWithEmptyEmail() throws Exception {
        User user = TestConfig.user();
             user.setEmail("");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email can not be empty"));;
    }

    @Test
    public void testUpdateUserWithEmptyPhone() throws Exception {
        User user = TestConfig.user();
             user.setPhone("");
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Phone can not be empty"));;
    }

    @Test
    public void testUpdateUserWithEmptyCreatedAt() throws Exception {
        User user = TestConfig.user();
             user.setCreatedAt(null);
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Created at is required"));;
    }

    @Test
    public void testUpdateUserWithFutureCreatedAt() throws Exception {
        User user = TestConfig.user();
        user.setCreatedAt(LocalDate.now().plusDays(1));
        String json = TestConfig.createMapper().writeValueAsString(user);

        when(service.updateUser(user.getId(), user)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Created at can not be a future date"));;
    }
}
