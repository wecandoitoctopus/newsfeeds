package hello.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private String title;
    private String content;
    private String postImage;
}
