package hello.newsfeed.user.dto;

import java.time.LocalDateTime;

public record UserResponse(Long id, String email, String username, String profileImage,
                           LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
