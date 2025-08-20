package hello.newsfeed.follow.repository;

import hello.newsfeed.follow.entity.Follow;
import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface FollowsRepository extends JpaRepository<Follow, Long> {

//    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    long deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // 나를 팔로우하는 사람들 - follower
    List<Follow> findByFollowingId(Long followingId);

    // 내가 팔로우하는 사람들 - following
    List<Follow> findByFollowerId(Long followerId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);



}
