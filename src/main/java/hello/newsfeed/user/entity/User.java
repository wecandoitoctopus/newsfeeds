package hello.newsfeed.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", indexes = {
        @Index(name = "user_email", columnList = "email", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name = "profileImage", length = 100)
    private String profileImage;

    // 회원가입용 User 객체 생성 //
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // 소프트 딜리트 방식 //
    private boolean deleted = false;

    public void softDeleted() {
        this.deleted = true;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
