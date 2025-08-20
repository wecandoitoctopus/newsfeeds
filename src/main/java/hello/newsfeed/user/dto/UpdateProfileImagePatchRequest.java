package hello.newsfeed.user.dto;

import jakarta.validation.constraints.*;

public record UpdateProfileImagePatchRequest(@NotBlank @Size(max = 100) String profileImage) {}
