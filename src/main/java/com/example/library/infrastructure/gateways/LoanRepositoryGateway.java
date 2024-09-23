package com.example.library.infrastructure.gateways;

import com.example.library.core.entities.Book;
import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.core.enums.LoanStatus;
import com.example.library.core.gateways.LoanGateway;
import com.example.library.infrastructure.gateways.mappers.BookEntityMapper;
import com.example.library.infrastructure.gateways.mappers.LoanEntityMapper;
import com.example.library.infrastructure.gateways.mappers.UserEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.LoanEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoanRepositoryGateway implements LoanGateway {
    private final LoanRepository repository;
    private final LoanEntityMapper mapper;
    private final BookEntityMapper bookMapper;
    private final UserEntityMapper userMapper;

    @Override
    public Loan createLoan(Loan loan) {
        LoanEntity entity = mapper.toEntity(loan);
        LoanEntity newLoan = repository.save(entity);
        return mapper.toLoan(newLoan);
    }

    @Override
    public Loan saveLoan(Loan loan) {
        LoanEntity entity = mapper.toEntity(loan);
        LoanEntity loanUpdated = repository.saveAndFlush(entity);
        return mapper.toLoan(loanUpdated);
    }

    @Override
    public Optional<Loan> getLoan(Long id) {
        Optional<LoanEntity> entity = repository.findLoanById(id);
        return entity.map(mapper::toLoan);
    }

    @Override
    public Optional<Loan> getActiveLoan(Book book) {
        BookEntity bookEntity = bookMapper.toEntity(book);
        Optional<LoanEntity> entity = repository.findByStatusAndBook(
                LoanStatus.ACTIVE, bookEntity
        );
        return entity.map(mapper::toLoan);
    }

    @Override
    public List<Loan> getLoansByUser(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        return repository
                .findLoansByUser(userEntity)
                .stream()
                .map(mapper::toLoan)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> getAllLoans() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toLoan)
                .collect(Collectors.toList());
    }
}
