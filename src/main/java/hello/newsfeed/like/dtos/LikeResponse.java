package hello.newsfeed.like.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    private boolean Liked;
    private Long likeCount;

    public LikeResponse(boolean Liked) {
        this.Liked = Liked;
        this.likeCount = 0L;
    }
}