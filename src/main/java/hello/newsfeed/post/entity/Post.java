package hello.newsfeed.post.entity;

import hello.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    // 일단 이미지를 스트링으로 대체하겠습니다.
    private String postImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Post(String title, String content, String postImage, User user) {
        this.title = title;
        this.content = content;
        this.postImage = postImage;
        this.user = user;
    }
    public static Post createPost(String title, String content, String postImage, User user) {
        return new Post(title, content, postImage, user);
    }
}
