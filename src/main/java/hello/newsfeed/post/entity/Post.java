package hello.newsfeed.post.entity;

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

    public Post(String title, String content, String postImage) {
        this.title = title;
        this.content = content;
        this.postImage = postImage;
    }
}
