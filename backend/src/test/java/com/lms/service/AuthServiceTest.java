package com.lms.service;

import com.lms.dto.request.LoginRequest;
import com.lms.dto.request.RegisterRequest;
import com.lms.dto.response.AuthResponse;
import com.lms.dto.response.UserResponse;
import com.lms.entity.User;
import com.lms.exception.ApiException;
import com.lms.repository.UserRepository;
import com.lms.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_hashesPasswordBeforeSaving() {
        var request = new RegisterRequest("Alice", "alice@example.com", "secret123");
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("$2a$10$hashed");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponse response = authService.register(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("$2a$10$hashed");
        assertThat(captor.getValue().getRole()).isEqualTo(User.Role.USER);
        assertThat(response.email()).isEqualTo("alice@example.com");
    }

    @Test
    void register_failsForDuplicateEmail() {
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(true);

        assertThatThrownBy(() ->
                authService.register(new RegisterRequest("Alice", "alice@example.com", "secret123")))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("already registered");

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_returnsTokenForValidCredentials() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("$2a$10$hashed");
        user.setRole(User.Role.USER);

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret123", "$2a$10$hashed")).thenReturn(true);
        when(jwtUtil.generateToken("alice@example.com", "USER")).thenReturn("jwt-token");

        AuthResponse response = authService.login(new LoginRequest("alice@example.com", "secret123"));

        assertThat(response.token()).isEqualTo("jwt-token");
        assertThat(response.user().name()).isEqualTo("Alice");
    }

    @Test
    void login_failsForWrongPassword() {
        User user = new User();
        user.setEmail("alice@example.com");
        user.setPassword("$2a$10$hashed");

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "$2a$10$hashed")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(new LoginRequest("alice@example.com", "wrong")))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void login_failsForUnknownEmail() {
        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(new LoginRequest("ghost@example.com", "x")))
                .isInstanceOf(BadCredentialsException.class);
    }
}
