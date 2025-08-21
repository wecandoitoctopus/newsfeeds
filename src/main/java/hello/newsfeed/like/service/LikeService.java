package hello.newsfeed.like.service;

import hello.newsfeed.like.dtos.LikeResponse;
import hello.newsfeed.like.entity.Like;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.like.repository.LikeRepository;
import hello.newsfeed.user.repository.UserRepository;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public LikeResponse toggleLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        boolean wasLiked = likeRepository.existsByUserIdAndPostId(userId, postId);

        if (wasLiked) {
            likeRepository.UnLike(userId, postId);
        } else {
            likeRepository.Like(userId, postId);
        }

        boolean currentLiked = !wasLiked;
        Long likeCount = likeRepository.countByPostId(postId);

        return new LikeResponse(currentLiked, likeCount);
    }

    @Transactional(readOnly = true)
    public LikeResponse getLikeInfo(Long userId, Long postId) {

        boolean liked = likeRepository.existsByUserIdAndPostId(userId, postId);
        Long likeCount = likeRepository.countByPostId(postId);

        return new LikeResponse(liked, likeCount);
    }
}