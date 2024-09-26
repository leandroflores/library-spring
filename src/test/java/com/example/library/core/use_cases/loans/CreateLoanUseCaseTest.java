package com.example.library.core.use_cases.loans;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.Loan;
import com.example.library.core.use_cases.loans.implementations.CreateLoanUseCaseImplementation;
import com.example.library.infrastructure.gateways.LoanRepositoryGateway;
import com.example.library.infrastructure.gateways.mappers.LoanEntityMapper;
import com.example.library.infrastructure.persistence.BookEntity;
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
public class CreateLoanUseCaseTest {

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
    private CreateLoanUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestLoanConfiguration.entityMapper();
        gateway = new LoanRepositoryGateway(
                repository,
                mapper,
                TestBookConfiguration.entityMapper(),
                TestUserConfiguration.entityMapper()
        );
        useCase = new CreateLoanUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        BookEntity book = bookRepository.save(TestBookConfiguration.bookEntity());
        UserEntity user = userRepository.save(TestUserConfiguration.userEntity());

        Loan loan = TestLoanConfiguration.loan();

        Loan newLoan = useCase.execute(loan);

        assert newLoan != null;
        assert newLoan.getStatus().equals(loan.getStatus());
        assert newLoan.getLoanDate().equals(loan.getLoanDate());
        assert newLoan.getBook().getTitle().equals(book.getTitle());
        assert newLoan.getUser().getEmail().equals(user.getEmail());
    }
}
