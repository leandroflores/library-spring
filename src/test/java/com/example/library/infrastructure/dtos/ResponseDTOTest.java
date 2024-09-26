package com.example.library.infrastructure.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ResponseDTOTest {

    @Mock
    private ResponseDTO response;

    @BeforeEach
    public void setUp() {
        response = new ResponseDTO("Success");
    }

    @Test
    public void testMessage() {
        assert response.message().equals("Success");
    }
}
