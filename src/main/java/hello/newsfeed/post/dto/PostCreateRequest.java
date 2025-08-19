package hello.newsfeed.post.dto;

import lombok.Getter;

@Getter
public class PostCreateRequest {

    private String title;
    private String content;
    private String postImage;
}
