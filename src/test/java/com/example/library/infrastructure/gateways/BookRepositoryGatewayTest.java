package com.example.library.infrastructure.gateways;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepositoryGatewayTest {

    @Autowired
    private BookRepository repository;

    @Mock
    private BookEntityMapper mapper;

    @Mock
    private BookRepositoryGateway gateway;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.entityMapper();
        gateway = new BookRepositoryGateway(repository, mapper);
    }

    @Test
    public void testCreateBook() {
        Book book = TestBookConfiguration.book();

        Book newBook = gateway.createBook(book);

        assert newBook.getTitle().equals(book.getTitle());
        assert newBook.getAuthor().equals(book.getAuthor());
        assert newBook.getIsbn().equals(book.getIsbn());
        assert newBook.getCategory().equals(book.getCategory());
        assert newBook.getPublishDate().equals(book.getPublishDate());
    }

    @Test
    public void testUpdateBook() {
        Book book = TestBookConfiguration.book();
             book.setTitle("wfedasç2");
        Book updatedBook = gateway.saveBook(book);

        assert updatedBook.getTitle().equals(book.getTitle());
        assert updatedBook.getAuthor().equals(book.getAuthor());
        assert updatedBook.getIsbn().equals(book.getIsbn());
        assert updatedBook.getCategory().equals(book.getCategory());
        assert updatedBook.getPublishDate().equals(book.getPublishDate());
    }

    @Test
    public void testDeleteBook() {
        Book book = gateway.createBook(TestBookConfiguration.book());

        gateway.deleteBook(book.getId());

        assert gateway.getBook(book.getId()).isEmpty();
    }

    @Test
    public void testGetBook() {
        Book book = gateway.createBook(TestBookConfiguration.book());
        Optional<Book> bookFound = gateway.getBook(book.getId());

        assert bookFound.isPresent();
        assert bookFound.get().getTitle().equals(book.getTitle());
        assert bookFound.get().getAuthor().equals(book.getAuthor());
        assert bookFound.get().getIsbn().equals(book.getIsbn());
        assert bookFound.get().getCategory().equals(book.getCategory());
        assert bookFound.get().getPublishDate().equals(book.getPublishDate());
    }

    @Test
    public void testGetAllBooks() {
        Book book = TestBookConfiguration.book();

        gateway.createBook(book);

        List<Book> books = gateway.getAllBooks();

        assert books.size() == 1;
        assert books.get(0).getTitle().equals(book.getTitle());
        assert books.get(0).getAuthor().equals(book.getAuthor());
        assert books.get(0).getIsbn().equals(book.getIsbn());
        assert books.get(0).getCategory().equals(book.getCategory());
        assert books.get(0).getPublishDate().equals(book.getPublishDate());
    }
}
