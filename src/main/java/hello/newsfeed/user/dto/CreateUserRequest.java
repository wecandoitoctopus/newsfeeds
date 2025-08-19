package hello.newsfeed.user.dto;

import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(max = 15) String username,
        @NotBlank @Size(min = 6, max = 100) String password
) {
}