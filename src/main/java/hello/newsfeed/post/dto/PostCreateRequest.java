package hello.newsfeed.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotNull
    @Size(max = 100)
    private String title;
    @NotNull
    @Size(max = 1000)
    private String content;
    @NotNull
    @Size(max = 100)
    private String postImage;
}
