package hello.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class ResponseFollowers {
    private Long id;
    private String name;
    private Long profileImage;

    public ResponseFollowers(Long id, String name, Long profileImage) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
    }

    public ResponseFollowers(Long id) {
        this.id = id;
    }
}
