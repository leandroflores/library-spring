package com.example.library.core.use_cases.loans;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.Loan;
import com.example.library.core.use_cases.loans.implementations.GetLoansByUserUseCaseImplementation;
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

import java.util.List;

@DataJpaTest
public class GetLoansByUserUseCaseTest {

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
    private GetLoansByUserUseCase useCase;

    @BeforeEach
    public void setUp() {
        mapper = TestLoanConfiguration.entityMapper();
        gateway = new LoanRepositoryGateway(
                repository,
                mapper,
                TestBookConfiguration.entityMapper(),
                TestUserConfiguration.entityMapper()
        );
        useCase = new GetLoansByUserUseCaseImplementation(gateway);
    }

    @Test
    public void testExecute() {
        BookEntity book = bookRepository.save(TestBookConfiguration.bookEntity());
        UserEntity user = userRepository.save(TestUserConfiguration.userEntity());
        LoanEntity loan = repository.save(TestLoanConfiguration.loanEntity());

        List<Loan> loans = useCase.execute(TestUserConfiguration.entityMapper().toUser(user));

        assert loans.size() == 1;
        assert loans.get(0).getStatus().equals(loan.getStatus());
        assert loans.get(0).getLoanDate().equals(loan.getLoanDate());
        assert loans.get(0).getBook().getTitle().equals(book.getTitle());
        assert loans.get(0).getUser().getEmail().equals(user.getEmail());
    }
}
