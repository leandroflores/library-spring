package com.example.library.infrastructure.gateways;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.Book;
import com.example.library.core.entities.Loan;
import com.example.library.core.entities.User;
import com.example.library.core.enums.LoanStatus;
import com.example.library.infrastructure.gateways.mappers.LoanEntityMapper;
import com.example.library.infrastructure.repositories.BookRepository;
import com.example.library.infrastructure.repositories.LoanRepository;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class LoanRepositoryGatewayTest {

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
    private BookRepositoryGateway bookGateway;

    @Mock
    private UserRepositoryGateway userGateway;

    @BeforeEach
    public void setUp() {
        mapper = TestLoanConfiguration.entityMapper();
        gateway = new LoanRepositoryGateway(
                repository,
                mapper,
                TestBookConfiguration.entityMapper(),
                TestUserConfiguration.entityMapper()
        );
        bookGateway = new BookRepositoryGateway(
                bookRepository,
                TestBookConfiguration.entityMapper()
        );
        userGateway = new UserRepositoryGateway(
                userRepository,
                TestUserConfiguration.entityMapper()
        );
    }

    @Test
    public void testCreateLoan() {
        Loan loan = TestLoanConfiguration.loan();

        Book book = bookGateway.createBook(TestBookConfiguration.book());
        User user = userGateway.createUser(TestUserConfiguration.user());
        Loan newLoan = gateway.createLoan(loan);

        assert newLoan.getLoanDate().equals(loan.getLoanDate());
        assert newLoan.getReturnDate().equals(loan.getReturnDate());
        assert newLoan.getStatus().equals(loan.getStatus());
        assert newLoan.getBook().getTitle().equals(book.getTitle());
        assert newLoan.getUser().getEmail().equals(user.getEmail());
    }

    @Test
    public void testSaveLoan() {
        Loan loan = TestLoanConfiguration.loan();
             loan.setStatus(LoanStatus.CANCELLED);

        Book book = bookGateway.createBook(TestBookConfiguration.book());
        User user = userGateway.createUser(TestUserConfiguration.user());
        Loan updatedLoan = gateway.saveLoan(loan);

        assert updatedLoan.getLoanDate().equals(loan.getLoanDate());
        assert updatedLoan.getReturnDate().equals(loan.getReturnDate());
        assert updatedLoan.getStatus().equals(loan.getStatus());
        assert updatedLoan.getBook().getTitle().equals(book.getTitle());
        assert updatedLoan.getUser().getEmail().equals(user.getEmail());
    }

    @Test
    public void testGetLoan() {
        Book book = bookGateway.createBook(TestBookConfiguration.book());
        User user = userGateway.createUser(TestUserConfiguration.user());
        Loan newLoan = gateway.createLoan(TestLoanConfiguration.loan());

        Optional<Loan> loanFound = gateway.getLoan(newLoan.getId());

        assert loanFound.isPresent();
        assert loanFound.get().getStatus().equals(newLoan.getStatus());
        assert loanFound.get().getLoanDate().equals(newLoan.getLoanDate());
        assert loanFound.get().getReturnDate().equals(newLoan.getReturnDate());
        assert loanFound.get().getBook().getTitle().equals(book.getTitle());
        assert loanFound.get().getUser().getEmail().equals(user.getEmail());
    }

    @Test
    public void testGetActiveLoan() {
        Book book = bookGateway.createBook(TestBookConfiguration.book());
        User user = userGateway.createUser(TestUserConfiguration.user());
        Loan newLoan = gateway.createLoan(TestLoanConfiguration.loan());
             newLoan.setStatus(LoanStatus.ACTIVE);

        Optional<Loan> loanFound = gateway.getActiveLoan(book);

        assert loanFound.isPresent();
        assert loanFound.get().getStatus().equals(newLoan.getStatus());
        assert loanFound.get().getLoanDate().equals(newLoan.getLoanDate());
        assert loanFound.get().getReturnDate().equals(newLoan.getReturnDate());
        assert loanFound.get().getBook().getTitle().equals(book.getTitle());
        assert loanFound.get().getUser().getEmail().equals(user.getEmail());
    }

    @Test
    public void testGetActiveLoanWithEmpty() {
        Book book = bookGateway.createBook(TestBookConfiguration.book());
        User user = userGateway.createUser(TestUserConfiguration.user());
        Loan loan = gateway.createLoan(TestLoanConfiguration.loan());
             loan.setStatus(LoanStatus.COMPLETED);

        gateway.saveLoan(loan);

        Optional<Loan> loanFound = gateway.getActiveLoan(book);

        assert loanFound.isEmpty();
    }

    @Test
    public void testGetLoansByUser() {
        Book book = bookGateway.createBook(TestBookConfiguration.book());
        User user = userGateway.createUser(TestUserConfiguration.user());
        Loan loan = gateway.createLoan(TestLoanConfiguration.loan());

        List<Loan> loans = gateway.getLoansByUser(user);

        assert loans.size() == 1;
        assert loans.get(0).getStatus().equals(loan.getStatus());
        assert loans.get(0).getLoanDate().equals(loan.getLoanDate());
        assert loans.get(0).getReturnDate().equals(loan.getReturnDate());
        assert loans.get(0).getBook().getTitle().equals(book.getTitle());
        assert loans.get(0).getUser().getEmail().equals(user.getEmail());
    }
}
