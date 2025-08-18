package hello.newsfeed.follow.repository;

import hello.newsfeed.follow.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowsRepository extends JpaRepository<Follows, Long> {
}
