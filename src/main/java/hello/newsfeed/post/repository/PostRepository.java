package hello.newsfeed.post.repository;

import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getAllByUser(User user);

    List<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds);

}
