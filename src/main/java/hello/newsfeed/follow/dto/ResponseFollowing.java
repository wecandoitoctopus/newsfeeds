package hello.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class ResponseFollowing {

    private Long id;
    private String name;
    private String profileImage;


    public ResponseFollowing(Long id, String name, String profileImage) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
    }

}
