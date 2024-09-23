package com.example.library.infrastructure.controllers.mappers;

import com.example.library.core.entities.Book;
import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.core.enums.LoanStatus;
import com.example.library.infrastructure.dtos.LoanCreateDTO;
import com.example.library.infrastructure.dtos.LoanDTO;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import com.example.library.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanMapper {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookEntityMapper bookMapper;
    private final UserEntityMapper userMapper;

    public LoanMapper(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookMapper = new BookEntityMapper();
        this.userMapper = new UserEntityMapper();
    }

    private User getUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            return null;
        }
        return userMapper.toUser(userEntity);
    }

    private Book getBook(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElse(null);
        if (bookEntity == null) {
            return null;
        }
        return bookMapper.toBook(bookEntity);
    }

    public Loan toNewLoan(LoanCreateDTO loan) {
        User user = getUser(loan.getUserId());
        Book book = getBook(loan.getBookId());
        return new Loan(
                null,
                LoanStatus.ACTIVE,
                LocalDate.now(),
                null,
                user,
                book
        );
    }

    public Loan toLoan(LoanDTO loan) {
        User user = getUser(loan.getUserId());
        Book book = getBook(loan.getBookId());
        return new Loan(
                null,
                loan.getStatus(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                user,
                book
        );
    }

    public LoanDTO toDTO(Loan loan) {
        return new LoanDTO(
                loan.getStatus(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getUser().getId(),
                loan.getBook().getId()
        );
    }
}
