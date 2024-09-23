package com.example.library.infrastructure.configuration;

import com.example.library.core.gateways.BookGateway;
import com.example.library.core.use_cases.books.*;
import com.example.library.core.use_cases.books.implementations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

    @Bean
    public GetAllBooksUseCase getAllBooksUseCase(BookGateway gateway) {
        return new GetAllBooksUseCaseImplementation(gateway);
    }

    @Bean
    public GetBookUseCase getBookUseCase(BookGateway gateway) {
        return new GetBookUseCaseImplementation(gateway);
    }

    @Bean
    public CreateBookUseCase createBookUseCase(BookGateway gateway) {
        return new CreateBookUseCaseImplementation(gateway);
    }

    @Bean
    public DeleteBookUseCase deleteBookUseCase(BookGateway gateway) {
        return new DeleteBookUseCaseImplementation(gateway);
    }

    @Bean
    public UpdateBookUseCase updateBookUseCase(BookGateway gateway) {
        return new UpdateBookUseCaseImplementation(gateway);
    }
}
