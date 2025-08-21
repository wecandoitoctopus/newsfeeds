package hello.newsfeed.like.controller;

import hello.newsfeed.like.dtos.LikeResponse;
import hello.newsfeed.like.service.LikeService;
import hello.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{feedId}/like")
    public ResponseEntity<LikeResponse> toggleLike(
            @PathVariable(name = "feedId") Long feedId,
            @SessionAttribute(name = "LOGIN_USER") Long userId) {

        LikeResponse response = likeService.toggleLike(userId, feedId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{feedId}/like")
    public ResponseEntity<LikeResponse> getLikeInfo(
            @PathVariable(name = "feedId") Long feedId,
            @SessionAttribute(name = "LOGIN_USER") Long userId) {

        LikeResponse response = likeService.getLikeInfo(userId, feedId);
        return ResponseEntity.ok(response);
    }
}