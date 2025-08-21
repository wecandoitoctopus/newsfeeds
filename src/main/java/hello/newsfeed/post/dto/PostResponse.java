package hello.newsfeed.post.dto;

import hello.newsfeed.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String postImage;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private PostResponse(Long id, String title, String content, String postImage,  LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postImage = postImage;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    public static PostResponse createPostResponse(Long id, String title, String content, String postImage, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new PostResponse(id, title, content, postImage, createdAt, modifiedAt);
    }
}
