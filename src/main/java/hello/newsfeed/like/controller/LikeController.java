package hello.newsfeed.like.controller;

import hello.newsfeed.like.dtos.LikeRequest;
import hello.newsfeed.like.dtos.LikeResponse;
import hello.newsfeed.like.service.LikeService;
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
            @PathVariable(name = "feedId") Long postId,
            @RequestParam Long userId) {

        LikeResponse response = likeService.toggleLike(userId, postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{feedId}/like")
    public ResponseEntity<LikeResponse> LikeInfo(
            @PathVariable(name = "feedId") Long postId,
            @RequestParam Long userId) {

        LikeResponse response = likeService.toggleLike(userId, postId);
        return ResponseEntity.ok(response);
    }
}
