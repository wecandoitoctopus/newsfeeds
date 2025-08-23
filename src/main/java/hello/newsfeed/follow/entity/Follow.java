package hello.newsfeed.follow.entity;

import hello.newsfeed.common.entity.BaseEntity;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Follow extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 앞(Many) = 클래스, 뒤(One) = 필드, 내가 작업하고 있는 클래스 기준으로 생각하기
    @JoinColumn(name="follower_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name="following_id", nullable = false)
    private User following;

    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
