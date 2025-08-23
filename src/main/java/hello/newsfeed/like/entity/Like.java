package hello.newsfeed.like.entity;

import hello.newsfeed.common.entity.BaseEntity;
import hello.newsfeed.post.entity.Post;
import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                        columnNames={"post_Id", "user_Id"}
                )
        }
)
public class Like extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
