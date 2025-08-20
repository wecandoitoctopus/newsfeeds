package hello.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class ResponseFollowing {

    private Long id;
    private String name;
    private Long profileImage;


    public ResponseFollowing(Long id, String name, Long profileImage) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
    }

    public ResponseFollowing(Long id) {
        this.id = id;
    }
}
