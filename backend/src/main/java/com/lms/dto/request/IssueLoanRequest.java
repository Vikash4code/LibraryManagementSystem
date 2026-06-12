package com.lms.dto.request;

import jakarta.validation.constraints.NotNull;

public record IssueLoanRequest(
        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Book id is required")
        Long bookId
) {
}
