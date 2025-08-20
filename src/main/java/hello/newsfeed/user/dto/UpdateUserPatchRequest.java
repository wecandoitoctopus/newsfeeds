package hello.newsfeed.user.dto;

import jakarta.validation.constraints.*;

public record UpdateUserPatchRequest(@NotBlank @Size(max = 15) String username) {}
