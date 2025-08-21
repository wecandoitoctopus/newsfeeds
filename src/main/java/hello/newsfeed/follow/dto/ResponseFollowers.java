package hello.newsfeed.follow.dto;

import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.post.dto.PostResponse;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResponseFollowers {
    private final Long id;
    private final String name;
    private final String profileImage;
    private final LocalDateTime createAt;
    private final LocalDateTime updatedAt;


    public ResponseFollowers(Long id, String name, String profileImage, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }
}
