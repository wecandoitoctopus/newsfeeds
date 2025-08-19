package hello.newsfeed.user.repository;

import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 회원가입 - 이메일 중복 검증용 //
    boolean existsByEmail(String email);
}
