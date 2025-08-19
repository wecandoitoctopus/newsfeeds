package hello.newsfeed.follow.service;

import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.follow.repository.FollowsRepository;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor // requird : 필수의 arguments : 필드, 자원 final이 무조건 달려있어야함
public class FollowService {

    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;

    // 매개변수는 순서가 존재한다 (순서 맞춰주기)
    public void addFollow(Long targetUserId, Long userId) {

        if (Objects.equals(targetUserId, userId)) {
            throw new RuntimeException("자기 자신을 팔로우 할 수 없습니다.");
        }
        // 이미 팔로우 중인지 확인 >> 쿼리 메서드 사용해야해서 나중에,,,

       User following = userRepository.findById(targetUserId).orElseThrow(
               () -> new RuntimeException("해당 유저는 존재하지 않습니다.")
       );
        User follower = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("해당 유저는 존재하지 않습니다.")
        );

        Follow follow = new Follow(follower, following);
        followsRepository.save(follow);


    }

    public void deleteFollow(Long targetUserId) {


    }
}
