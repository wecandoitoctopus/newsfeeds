package hello.newsfeed.user.dto;

import jakarta.validation.constraints.*;

public record ChangePasswordPatchRequest(@NotBlank @Size(min = 6, max = 60) String newPassword) {}
