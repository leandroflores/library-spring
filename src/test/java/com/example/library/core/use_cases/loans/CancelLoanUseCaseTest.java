package com.example.library.core.use_cases.loans;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.Loan;
import com.example.library.core.enums.LoanStatus;
import com.example.library.core.use_cases.loans.implementations.CancelLoanUseCaseImplementation;
import com.example.library.infrastructure.gateways.LoanRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.LoanEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.LoanEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import com.example.library.infrastructure.repositories.LoanRepository;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CancelLoanUseCaseTest {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private LoanEntityMapper mapper;

    @Mock
    private LoanRepositoryGateway gateway;

    @Mock
    private CancelLoanUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestLoanConfiguration.entityMapper();
        gateway = new LoanRepositoryGateway(
                repository,
                mapper,
                TestBookConfiguration.entityMapper(),
                TestUserConfiguration.entityMapper()
        );
        useCase = new CancelLoanUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        BookEntity book = bookRepository.save(TestBookConfiguration.bookEntity());
        UserEntity user = userRepository.save(TestUserConfiguration.userEntity());
        LoanEntity loan = repository.save(TestLoanConfiguration.loanEntity());

        Loan canceledLoan = useCase.execute(mapper.toLoan(loan));

        assert canceledLoan.getStatus().equals(LoanStatus.CANCELLED);
        assert canceledLoan.getLoanDate().equals(loan.getLoanDate());
        assert canceledLoan.getBook().getTitle().equals(book.getTitle());
        assert canceledLoan.getUser().getEmail().equals(user.getEmail());
    }
}