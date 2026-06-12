package com.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.config.SecurityConfig;
import com.lms.dto.request.BookRequest;
import com.lms.dto.response.BookResponse;
import com.lms.security.CustomUserDetailsService;
import com.lms.security.JwtAuthFilter;
import com.lms.security.JwtUtil;
import com.lms.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    // needed by the imported JwtAuthFilter
    @MockitoBean
    private JwtUtil jwtUtil;
    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private final BookResponse sampleBook = new BookResponse(
            1L, "Clean Code", "Robert C. Martin", "9780132350884",
            "Programming", 5, 5, null, null);

    @Test
    void getBooks_isPublicAndReturnsPage() throws Exception {
        when(bookService.getBooks(any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(sampleBook)));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Clean Code"))
                .andExpect(jsonPath("$.content[0].availableCopies").value(5));
    }

    @Test
    void addBook_requiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isForbidden()); // anonymous request is rejected
    }

    @Test
    @WithMockUser(roles = "USER")
    void addBook_forbiddenForNormalUsers() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addBook_createdForAdmin() throws Exception {
        when(bookService.addBook(any(BookRequest.class))).thenReturn(sampleBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addBook_validationErrorReturnsFieldMap() throws Exception {
        var invalid = new BookRequest("", "", null, null, 0, null, null);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.title").exists())
                .andExpect(jsonPath("$.fieldErrors.totalCopies").exists());
    }

    private BookRequest validRequest() {
        return new BookRequest("Clean Code", "Robert C. Martin", "9780132350884",
                "Programming", 5, null, null);
    }
}
