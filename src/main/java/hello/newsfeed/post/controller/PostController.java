package hello.newsfeed.post.controller;

import hello.newsfeed.post.dto.PostCreateRequest;
import hello.newsfeed.post.dto.PostResponse;
import hello.newsfeed.post.dto.PostUpdateRequest;
import hello.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 피드 생성
    public ResponseEntity<PostResponse> createPost(
            @RequestBody PostCreateRequest postCreateRequest
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(postService.createPost(userId, postCreateRequest));
    }

    // 피드 전체 조회
    public ResponseEntity<List<PostResponse>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    // 피드 단일 조회
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 피드 수정
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(postService.updatePost(userId, postId, postUpdateRequest));
    }

    // 피드 삭제
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        Long userId = 1L;
        postService.deletePost(postId, userId);
        return ResponseEntity.ok().build();
    }
}
