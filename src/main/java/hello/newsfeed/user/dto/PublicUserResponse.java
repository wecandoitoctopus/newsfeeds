package hello.newsfeed.user.dto;

import java.time.LocalDateTime;

public record PublicUserResponse(Long id, String username, String profileImage,
                                 LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
