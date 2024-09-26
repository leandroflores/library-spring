package com.example.library.infrastructure.controllers.mappers;

import com.example.library.configuration.TestBookConfiguration;
import com.example.library.configuration.TestLoanConfiguration;
import com.example.library.configuration.TestUserConfiguration;
import com.example.library.core.entities.Loan;
import com.example.library.core.enums.LoanStatus;
import com.example.library.infrastructure.dtos.LoanCreateDTO;
import com.example.library.infrastructure.dtos.LoanDTO;
import com.example.library.infrastructure.persistence.BookEntity;
import com.example.library.infrastructure.persistence.UserEntity;
import com.example.library.infrastructure.repositories.BookRepository;
import com.example.library.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LoanMapperTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private LoanMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new LoanMapper(bookRepository, userRepository);
    }

    @Test
    public void testToNewLoan() {

        LoanCreateDTO loanCreate = TestLoanConfiguration.loanCreateDTO();

        Loan loan = mapper.toNewLoan(loanCreate);

        assert loan.getId() == null;
        assert loan.getStatus().equals(LoanStatus.ACTIVE);
        assert loan.getReturnDate() == null;
    }

    @Test
    public void testToLoan() {
        BookEntity book = bookRepository.save(TestBookConfiguration.bookEntity());
        UserEntity user = userRepository.save(TestUserConfiguration.userEntity());

        LoanDTO loadDTO = new LoanDTO(
                TestLoanConfiguration.loanStatus(),
                TestLoanConfiguration.loanDate(),
                TestLoanConfiguration.loanReturnDate(),
                book.getId(),
                user.getId()
        );

        Loan loan = mapper.toLoan(loadDTO);

        assert loan.getStatus().equals(TestLoanConfiguration.loanStatus());
        assert loan.getLoanDate().equals(TestLoanConfiguration.loanDate());
        assert loan.getReturnDate().equals(TestLoanConfiguration.loanReturnDate());
        assert loan.getBook().getTitle().equals(book.getTitle());
        assert loan.getUser().getEmail().equals(user.getEmail());
    }

    @Test
    public void testToDTO() {
        BookEntity book = bookRepository.save(TestBookConfiguration.bookEntity());
        UserEntity user = userRepository.save(TestUserConfiguration.userEntity());

        Loan loan = TestLoanConfiguration.loan();

        LoanDTO loanDTO = mapper.toDTO(loan);

        assert loanDTO.getStatus().equals(TestLoanConfiguration.loanStatus());
        assert loanDTO.getLoanDate().equals(TestLoanConfiguration.loanDate());
        assert loanDTO.getReturnDate().equals(TestLoanConfiguration.loanReturnDate());
        assert loanDTO.getBookId().equals(book.getId());
        assert loanDTO.getUserId().equals(user.getId());
    }
}
