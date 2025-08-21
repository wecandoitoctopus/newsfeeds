package hello.newsfeed.follow.service;

import hello.newsfeed.follow.dto.ResponseFollowers;
import hello.newsfeed.follow.dto.ResponseFollowing;
import hello.newsfeed.follow.dto.ResponseFollowingStatus;
import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.repository.FollowsRepository;
import hello.newsfeed.post.dto.PostResponse;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.post.repository.PostRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // requird : 필수의 arguments : 필드, 자원 final이 무조건 달려있어야함
public class FollowService {

    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    // 매개변수는 순서가 존재한다 (순서 맞춰주기)
    public void addFollow(Long targetUserId, Long userId) {

        if (Objects.equals(targetUserId, userId)) {
            throw new RuntimeException("자기 자신을 팔로우 할 수 없습니다.");
        }
        // 이미 팔로우 중인지 확인
        if (followsRepository.existsByFollowerIdAndFollowingId(userId, targetUserId)) {
            throw new RuntimeException("이미 팔로우 중입니다.");
        }

       User following = userRepository.findById(targetUserId).orElseThrow(
               () -> new RuntimeException("해당 유저는 존재하지 않습니다.")
       );
        User follower = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("해당 유저는 존재하지 않습니다.")
        );

        Follow follow = new Follow(follower, following);
        followsRepository.save(follow);

    }

    @Transactional
    public void deleteFollow(Long targetUserId, Long userId) {

        if (Objects.equals(targetUserId, userId)) {
            throw new RuntimeException("자기 자신을 삭제할 수 없습니다.");
        }

//        User following = userRepository.findById(targetUserId).orElseThrow(
//                () -> new RuntimeException("해당 유저는 존재하지 않습니다.")
//        );
//
//        User follower = userRepository.findById(userId).orElseThrow(
//                () -> new RuntimeException("해당 유저는 존재하지 않습니다.")
//        );
//
//        Follow follow = followsRepository.findByFollowerAndFollowing(follower, following).orElseThrow(
//                () -> new RuntimeException("ddd")
//        );
//        followsRepository.delete(follow);

        Long result = followsRepository.deleteByFollowerIdAndFollowingId(userId, targetUserId);

//        if (result == 0) {
//            throw new RuntimeException("팔로우 취소가 실패했습니다.");
//        }
    }

    // 나를 팔로우한 사람들
    @Transactional
    public List<ResponseFollowers> getFollowers(Long userId) {
        List<ResponseFollowers> followersReadResponseList = new ArrayList<>();

        List<Follow> followerList = followsRepository.findByFollowingId(userId);
        for (Follow follow : followerList) {
            ResponseFollowers responseFollowers = new ResponseFollowers(
                    follow.getFollower().getId(), // 이름 프로필사진
                    follow.getFollower().getUsername(),
                    follow.getFollower().getProfileImage(),
                    follow.getCreatedAt(),
                    follow.getModifiedAt()
            );
            followersReadResponseList.add(responseFollowers);
        }
        return followersReadResponseList;
    }

    // 내가 팔로잉한 사람들
    @Transactional
    public List<ResponseFollowing> getFollowing(Long userId) {
        List<ResponseFollowing> followingReadResponseList = new ArrayList<>();

        List<Follow> followingList = followsRepository.findByFollowerId(userId);
        for (Follow follow : followingList) {
            ResponseFollowing responseFollowing = new ResponseFollowing(
                    follow.getFollowing().getId(),
                    follow.getFollowing().getUsername(),
                    follow.getFollowing().getProfileImage(),
                    follow.getCreatedAt(),
                    follow.getModifiedAt()
            );
            followingReadResponseList.add(responseFollowing);
        }
        return followingReadResponseList;
    }

    // 내가 이 사람을 팔로우 관계인지 확인
    public ResponseFollowingStatus isFollowing(Long userId, Long targetUserId) {
        boolean isFollowing = followsRepository.existsByFollowerIdAndFollowingId(userId, targetUserId);
        return new ResponseFollowingStatus(isFollowing);
    }
    
    // 내가 팔로잉한 사람들의 게시글
    public List<PostResponse> getPostsByFollowing(Long userId) {
        List<Long> followingIds = getFollowingUserIds(userId);
        List<PostResponse> postResponseList = new ArrayList<>();
        List<Post> posts = postRepository.findByUserIdInOrderByCreatedAtDesc(followingIds);
        for (Post post : posts) {
            PostResponse postResponse = PostResponse.createPostResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getPostImage(),
                    post.getCreatedAt(),
                    post.getModifiedAt()
            );
            postResponseList.add(postResponse);
        }
        return postResponseList;
    }

    // 내가 팔로우한 유저들의 ID 목록 가져오기 (내부적으로 사용하는 코드)
    private List<Long> getFollowingUserIds(Long userId) {
        List<Follow> follows = followsRepository.findByFollowerId(userId);
        return follows.stream()
                .map(f -> f.getFollowing().getId())
                .collect(Collectors.toList());
    }
}
