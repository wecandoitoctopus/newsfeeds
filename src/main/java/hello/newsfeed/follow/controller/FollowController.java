package hello.newsfeed.follow.controller;

import hello.newsfeed.follow.dto.ResponseFollowers;
import hello.newsfeed.follow.dto.ResponseFollowing;
import hello.newsfeed.follow.dto.ResponseFollowingStatus;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

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

    @GetMapping("/{targetUserId}/relationship")
    public ResponseEntity<ResponseFollowingStatus> isFollowing(@PathVariable Long targetUserId, @SessionAttribute(name = "LOGIN_USER") Long userId) {
        ResponseFollowingStatus responseFollowingStatus = followService.isFollowing(userId, targetUserId);
        return ResponseEntity.ok(responseFollowingStatus);
    }
}
