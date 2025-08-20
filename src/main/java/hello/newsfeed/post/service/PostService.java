package hello.newsfeed.post.service;

import hello.newsfeed.post.dto.PostCreateRequest;
import hello.newsfeed.post.dto.PostResponse;
import hello.newsfeed.post.dto.PostUpdateRequest;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 피드 생성
    public PostResponse createPost(Long userId, PostCreateRequest postCreateRequest) {
        User user = userRepository.getReferenceById(userId);
        Post post = postRepository.save(
                Post.createPost(
                        postCreateRequest.getTitle(),
                        postCreateRequest.getContent(),
                        postCreateRequest.getPostImage(),
                        user
                )
        );
        return PostResponse.createPostResponse(post);
    }

    // 피드 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponse> getAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostResponse::createPostResponse).toList();
    }

    // 피드 단일 조회
    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 피드입니다.")
        );
        return PostResponse.createPostResponse(post);
    }

    // 피드 수정
    public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest postUpdateRequest) {
        User user = userRepository.getReferenceById(userId);
        List<Post> posts = postRepository.getAllByUser(user);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 피드입니다.")
        );
        post.updateAll(postUpdateRequest);
        return PostResponse.createPostResponse(post);
    }

    // 피드 삭제
    public void deletePost(Long userId, Long postId) {
        User user = userRepository.getReferenceById(userId);
        List<Post> posts = postRepository.getAllByUser(user);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 피드입니다.")
        );
        postRepository.delete(post);
    }
}
