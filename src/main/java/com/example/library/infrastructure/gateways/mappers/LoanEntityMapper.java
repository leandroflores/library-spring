package com.example.library.infrastructure.gateways.mappers;

import com.example.library.core.entities.Book;
import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.LoanEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanEntityMapper {
    private final BookEntityMapper bookEntityMapper;
    private final UserEntityMapper userEntityMapper;

    public LoanEntityMapper(BookEntityMapper bookEntityMapper, UserEntityMapper userEntityMapper) {
        this.bookEntityMapper = bookEntityMapper;
        this.userEntityMapper = userEntityMapper;
    }

    public LoanEntity toEntity(Loan loan) {
        UserEntity user = userEntityMapper.toEntity(loan.getUser());
        BookEntity book = bookEntityMapper.toEntity(loan.getBook());
        return new LoanEntity(
                loan.getId(),
                loan.getStatus(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                user,
                book
        );
    }

    public Loan toLoan(LoanEntity entity) {
        User user = userEntityMapper.toUser(entity.getUser());
        Book book = bookEntityMapper.toBook(entity.getBook());
        return new Loan(
            entity.getId(),
            entity.getStatus(),
            entity.getLoanDate(),
            entity.getReturnDate(),
            user,
            book
        );
    }
}
