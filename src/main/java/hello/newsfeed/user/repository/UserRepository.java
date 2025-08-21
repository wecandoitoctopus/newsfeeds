package hello.newsfeed.user.repository;

import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  
    // 회원가입 - 이메일 중복 체크 //
    boolean existsByEmailAndDeletedFalse(String email);  // -> 소프트딜리트용 (탈퇴안한 이메일만 확인)

    // 로그인 시 이메일 조회 - 탈퇴 계정 로그인 불가//
    Optional<User> findByEmailAndDeletedFalse(String email);  // -> 소프트딜리트용

    // 모든 이메일(탈퇴 포함) 확인 - 아이디(이메일) 재사용 불가//
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
