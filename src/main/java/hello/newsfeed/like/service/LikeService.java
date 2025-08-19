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

    //속성 + final 자동 생성 의존성 주입
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    //기
    public LikeResponse toggleLike(Long userId, Long postId) {

        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        boolean liked = likeRepository.existsByUserAndPostId(user, postId);

        if (liked) {
            //좋아요가 이미 했으면 취소
            likeRepository.UnLike(userId, postId);
        } else {
            //좋아요가 없으면 추가
            likeRepository.Like(userId, postId);
        }

        Long likeCount = likeRepository.countByPostId(postId);
        return new LikeResponse(liked, likeCount);
    }

//    public LikeResponse getLikeInfo(Long userId, Long postId) {
//
//        postRepository.findById(postId).orElseThrow();
//
//        boolean liked = likeRepository.existByUserIdAndPostId(userId, postId);
//        Long likeCount = likeRepository.countByPostId(postId);
//
//        return new LikeResponse(liked, likeCount);
//    }
}