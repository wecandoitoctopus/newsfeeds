package hello.newsfeed.post.controller;

import hello.newsfeed.post.dto.PostCreateRequest;
import hello.newsfeed.post.dto.PostResponse;
import hello.newsfeed.post.dto.PostUpdateRequest;
import hello.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 피드 생성
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostCreateRequest postCreateRequest,
            @SessionAttribute(name = "LOGIN_USER") Long userId
    ) {
        return ResponseEntity.ok(postService.createPost(userId, postCreateRequest));
    }

    // 피드 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    // 피드 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 피드 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest postUpdateRequest,
            @SessionAttribute(name = "LOGIN_USER") Long userId
    ) {
        return ResponseEntity.ok(postService.updatePost(userId, postId, postUpdateRequest));
    }

    // 피드 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @SessionAttribute(name = "LOGIN_USER") Long userId
    ) {
        postService.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
