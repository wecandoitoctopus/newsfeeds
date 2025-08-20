package hello.newsfeed.auth.service;

import hello.newsfeed.auth.dto.AuthRequest;
import hello.newsfeed.auth.dto.AuthResponse;
import hello.newsfeed.auth.dto.SignResponse;
import hello.newsfeed.common.config.PasswordEncoder;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX =
            "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct}).{8,}$";

    // 회원가입 //
    @Transactional
    public SignResponse save(AuthRequest authRequest) {
        // 중복 이메일 예외처리 // -500에러
        // if (userRepository.existsByEmail(authRequest.getEmail())) {           -> 하드 딜리트
        //    throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        //}
        // 사용 중인 이메일 예외처리 (탈퇴x)
        if (userRepository.existsByEmailAndDeletedFalse(authRequest.getEmail())) {  // -> 소프트 딜리트
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }
        // 이미 탈퇴한 이메일 예외처리 (재가입 불가)  -> 최종 추가  => 소프트 딜리트
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new IllegalArgumentException("이미 탈퇴한 이메일입니다. 재가입할 수 없습니다.");
        }

        // 이메일 형식 예외처리 // - 400에러
        if (authRequest.getEmail() == null || !authRequest.getEmail().matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
        // 비밀번호 형식 예외처리 // - 400에러
        if (authRequest.getPassword() == null || !authRequest.getPassword().matches(PASSWORD_REGEX)) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
        // 비밀번호 인코딩 //
        String encodedPassword = passwordEncoder.encode(authRequest.getPassword());
        User user = new User(authRequest.getName(), authRequest.getEmail(), encodedPassword);

        userRepository.save(user);
        return new SignResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // 로그인 //
    @Transactional(readOnly = true)                                                    // 트랜잭션(읽기 전용)
    public SignResponse login(AuthRequest authRequest) {
        // 하드 딜리트 //
        // User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(
        //         () -> new ResponseStatusException(                                     // 이메일 불일치 -> 401 반환
        //                 HttpStatus.UNAUTHORIZED, "이메일이 틀렸습니다.")
        // );

        User user = userRepository.findByEmailAndDeletedFalse(authRequest.getEmail()).orElseThrow(
                () -> new ResponseStatusException(                                     // 이메일 불일치 -> 401 반환
                        HttpStatus.UNAUTHORIZED, "이메일이 틀렸습니다.")
        );

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."
            );
        }

        return new SignResponse(user.getId(), user.getName(),user.getEmail());           // 인증 성공 시 이름, 이메일 담아 반환
    }

    // 회원 탈퇴 //
    @Transactional
    public ResponseEntity<AuthResponse<Void>> deleteMe(Long userId, String password) {
        // 1. 사용자 조회 -> 계정 존재 여부 확인 //
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 계정입니다.")
        );

        // 2. 비밀번호 검증 //
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."
            );
        }
        // 3. 삭제
        userRepository.delete(user);

        // 4. 공동 응답 리턴 (데이터x) //
        return ResponseEntity.ok(
                AuthResponse.<Void>success("회원 탈출 성공",null));
    }

}
