package hello.newsfeed.user.repository;

import hello.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 회원가입 - 이메일 중복 검증용 //
    // boolean existsByEmail(String email);  -> 하드딜리트용
    boolean existsByEmailAndDeletedFalse(String email);  // -> 소프트딜리트용 (탈퇴안한 이메일만 확인)

    // 이메일로 단건 조회 - 로그인//
    // Optional<User> findByEmail(String email);  -> 하드딜리트용
    Optional<User> findByEmailAndDeletedFalse(String email);  // -> 소프트딜리트용

    // 모든 이메일(탈퇴 포함) 확인
    boolean existsByEmail(String email); // -> 소프트딜리트용 (탈퇴 포함 모든 이메일 확인)
}

// 하드 -> 소프트 변경했으나 아이디 재사용 해결 안됨.