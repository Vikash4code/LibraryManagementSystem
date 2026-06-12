package com.lms.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 255)
        String title,

        @NotBlank(message = "Author is required")
        @Size(max = 255)
        String author,

        @Size(max = 20)
        String isbn,

        @Size(max = 50)
        String category,

        @Min(value = 1, message = "Total copies must be at least 1")
        int totalCopies,

        @Size(max = 500)
        String coverImageUrl,

        @Size(max = 2000)
        String description
) {
}
