package hello.newsfeed.like.dtos;

import hello.newsfeed.like.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    private boolean liked;
    private Long likeCount;

    public LikeResponse(boolean liked) {
        this.liked = liked;
        this.likeCount = 0L;
    }
}