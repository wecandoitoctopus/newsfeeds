package hello.newsfeed.like.repository;

import hello.newsfeed.like.entity.Like;
import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByPostId(@Param("postId") Long postId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Modifying
    @Query(value = "INSERT INTO LIKES(post_Id, user_Id) VALUES(:postId, :userId)", nativeQuery = true)
    void Like(@Param("userId") Long userId, @Param("postId") Long postId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_Id = :postId AND user_Id = :userId", nativeQuery = true)
    void UnLike(@Param("userId") Long userId, @Param("postId") Long postId);
}
