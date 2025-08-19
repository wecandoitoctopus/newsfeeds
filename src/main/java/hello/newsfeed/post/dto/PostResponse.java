package hello.newsfeed.post.dto;

import hello.newsfeed.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String postImage;

    private PostResponse(Long id, String title, String content, String postImage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postImage = postImage;
    }
    public PostResponse createPostResponse(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getPostImage());
    }
}
