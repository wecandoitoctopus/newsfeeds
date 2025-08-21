package hello.newsfeed.follow.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseFollowing {

    private Long id;
    private String name;
    private String profileImage;
    private final LocalDateTime createAt;
    private final LocalDateTime updatedAt;


    public ResponseFollowing(Long id, String name, String profileImage, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

}
