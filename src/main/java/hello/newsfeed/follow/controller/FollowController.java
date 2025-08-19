package hello.newsfeed.follow.controller;

import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{targetUserId}/follow")
    public ResponseEntity<Void> addFollow(@PathVariable Long targetUserId) {
        Long userId = 1L;
        followService.addFollow(targetUserId, userId);
        return ResponseEntity.noContent().build(); // 204로 반환
    }

    @DeleteMapping("/{targetUserId}/follow")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long targetUserId) {
        Long Id = 1L;
        followService.deleteFollow(targetUserId);
        return ResponseEntity.noContent().build();
    }



}
