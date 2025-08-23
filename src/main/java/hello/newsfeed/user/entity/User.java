package hello.newsfeed.user.entity;

import hello.newsfeed.common.entity.BaseEntity;
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
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100)
    private String profileImage;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Builder.Default
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
