package hello.newsfeed.follow.controller;

import hello.newsfeed.follow.dto.ResponseFollowers;
import hello.newsfeed.follow.dto.ResponseFollowing;
import hello.newsfeed.follow.dto.ResponseFollowingStatus;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.service.FollowService;
import hello.newsfeed.post.dto.PostResponse;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final PostRepository postRepository;

    @PostMapping("/{targetUserId}/follow")
    public ResponseEntity<Void> addFollow(@PathVariable Long targetUserId, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        followService.addFollow(targetUserId, userId);
        return ResponseEntity.noContent().build(); // 204로 반환
    }

    @DeleteMapping("/{targetUserId}/follow")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long targetUserId, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        followService.deleteFollow(targetUserId, userId);
        return ResponseEntity.noContent().build();
    }

    // 나를 팔로워한 사람들
    @GetMapping("/me/followers")
    public ResponseEntity<List<ResponseFollowers>> getFollowers(@SessionAttribute(name = "LOGIN_USER") Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    // 내가 팔로잉한 사람들
    @GetMapping("/me/following")
    public ResponseEntity<List<ResponseFollowing>> getFollowing(@SessionAttribute(name = "LOGIN_USER") Long userId) {
        return ResponseEntity.ok(followService.getFollowing(userId));
    }

    // 나와 팔로우 관계인지 확인
    @GetMapping("/{targetUserId}/relationship")
    public ResponseEntity<ResponseFollowingStatus> isFollowing(@PathVariable Long targetUserId, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        ResponseFollowingStatus responseFollowingStatus = followService.isFollowing(userId, targetUserId);
        return ResponseEntity.ok(responseFollowingStatus);
    }

    // 내가 팔로우한 사람들의 게시물을 최신순으로 조회
    @GetMapping("/follow/posts")
    public ResponseEntity<List<PostResponse>> getFollowersPosts (@SessionAttribute(name = "LOGIN_USER") Long userId) {
        return ResponseEntity.ok(followService.getPostsByFollowing(userId));
    }
}