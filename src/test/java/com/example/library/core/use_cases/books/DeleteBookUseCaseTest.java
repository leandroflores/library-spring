package com.example.library.core.use_cases.books;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.core.use_cases.books.implementations.DeleteBookUseCaseImplementation;
import com.example.library.infrastructure.gateways.BookRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DeleteBookUseCaseTest {

    @Autowired
    private BookRepository repository;

    @Mock
    private BookEntityMapper mapper;

    @Mock
    private BookRepositoryGateway gateway;

    @Mock
    private DeleteBookUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestBookConfiguration.entityMapper();
        gateway = new BookRepositoryGateway(repository, mapper);
        useCase = new DeleteBookUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        BookEntity book = repository.save(mapper.toEntity(TestBookConfiguration.book()));

        boolean deleted = useCase.execute(book.getId());

        assert deleted;
        assert !repository.existsById(book.getId());
    }
}
