package com.example.library.controllers;

import com.example.library.TestConfig;
import com.example.library.domain.Book;
import com.example.library.services.BookService;
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

public class BookControllerTest {

    private MockMvc mvc;

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetBooks() throws Exception {
        List<Book> books = TestConfig.books();
        Book book = books.get(0);

        when(service.getAllBooks()).thenReturn(books);

        mvc.perform(MockMvcRequestBuilders.get("/books/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publishDate").value(book.publishDateFormatted()));
    }

    @Test
    public void testGetBook() throws Exception {
        Book book = TestConfig.book();

        when(service.getBookById(book.getId())).thenReturn(Optional.of(book));

        mvc.perform(MockMvcRequestBuilders.get("/books/" + book.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("publishDate").value(book.publishDateFormatted()));
    }

    @Test
    public void testGetNotFoundBook() throws Exception {
        Book book = TestConfig.book();

        when(service.getBookById(book.getId())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/books/" + book.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book not found"));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = TestConfig.book();
        String json = TestConfig.bookJson();

        when(service.createBook(book)).thenReturn(book);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
           .contentType(MediaType.APPLICATION_JSON)
           .content(json))
           .andExpect(MockMvcResultMatchers.status().isCreated())
           .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    public void testCreateBookWithNullTitle() throws Exception {
        Book book = TestConfig.book().clone();
             book.setTitle(null);
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Title is required"));
    }

    @Test
    public void testCreateBookWithEmptyTitle() throws Exception {
        Book book = TestConfig.book().clone();
        book.setTitle("");
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Title can not be empty"));
    }

    @Test
    public void testCreateBookWithNullAuthor() throws Exception {
        Book book = TestConfig.book().clone();
        book.setAuthor(null);
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Author is required"));
    }

    @Test
    public void testCreateBookWithEmptyAuthor() throws Exception {
        Book book = TestConfig.book().clone();
             book.setAuthor("");
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Author can not be empty"));
    }

    @Test
    public void testCreateBookWithNullISBN() throws Exception {
        Book book = TestConfig.book().clone();
             book.setIsbn(null);
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("ISBN is required"));
    }

    @Test
    public void testCreateBookWithEmptyISBN() throws Exception {
        Book book = TestConfig.book().clone();
             book.setIsbn("");
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("ISBN can not be empty"));
    }

    @Test
    public void testCreateBookWithNullCategory() throws Exception {
        Book book = TestConfig.book().clone();
        book.setCategory(null);
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Category is required"));
    }

    @Test
    public void testCreateBookWithEmptyCategory() throws Exception {
        Book book = TestConfig.book().clone();
             book.setCategory(null);
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Category can not be empty"));
    }

    @Test
    public void testCreateBookWithFuturePublishDate() throws Exception {
        Book book = TestConfig.book().clone();
        book.setPublishDate(LocalDate.MAX);
        String bookJson = TestConfig.createMapper().writeValueAsString(book);

        System.out.println(bookJson);

        when(service.createBook(book)).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.post("/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Publish date can not be a future date"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = TestConfig.book();

        when(service.deleteBook(book.getId())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/books/" + book.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book deleted successfully"));
    }

    @Test
    public void testDeleteNotFoundBook() throws Exception {
        Book book = TestConfig.book();

        when(service.deleteBook(book.getId())).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/books/" + book.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book not found"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = TestConfig.book();
             book.setIsbn("9780197723821");
        String json = TestConfig.createMapper().writeValueAsString(book);

        when(service.updateBook(book.getId(), book)).thenReturn(Optional.of(book));

        mvc.perform(MockMvcRequestBuilders.put("/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("publishDate").value(book.publishDateFormatted()));
    }

    @Test
    public void testUpdateNotFoundBook() throws Exception {
        Book book = TestConfig.book();
             book.setIsbn("9780197723821");
        String bookJson = TestConfig.createMapper().writeValueAsString(book);

        when(service.updateBook(book.getId(), book)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Book not found"));;
    }

}
