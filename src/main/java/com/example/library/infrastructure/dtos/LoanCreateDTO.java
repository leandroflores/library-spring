package com.example.library.infrastructure.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanCreateDTO {
        private Long userId;
        private Long bookId;

}
