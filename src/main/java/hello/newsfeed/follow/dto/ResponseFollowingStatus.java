package hello.newsfeed.follow.dto;

// getter 안 써도 record가 알아서 만들어줌
public record ResponseFollowingStatus(boolean isFollowing) {
}
