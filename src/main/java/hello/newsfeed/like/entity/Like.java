package hello.newsfeed.like.entity;

import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes",
                        columnNames={"post_id", "user_id"}
                )
        }
)
public class Like {

//    @Transient // 칼럼이 만들어지지 않는다.
//    private Long likeCount;
//
//    @Transient
//    private boolean likeState;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @CreationTimestamp
//    private Timestamp createAt;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
