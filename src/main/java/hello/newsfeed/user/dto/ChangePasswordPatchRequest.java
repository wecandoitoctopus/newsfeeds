package hello.newsfeed.user.dto;

import jakarta.validation.constraints.*;

public record ChangePasswordPatchRequest(
        @NotBlank(message = "New password is required.")
        @Size(min = 6, max = 25, message = "Current password must be at least 6 characters")
        String currentPassword,

        @NotBlank(message = "New password is required.")
        @Size(min = 6, max = 25, message = "New password must be at least 6 characters")
        String newPassword) {
}