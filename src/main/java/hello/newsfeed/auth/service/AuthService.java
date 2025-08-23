package hello.newsfeed.auth.service;

import hello.newsfeed.auth.dto.AuthRequest;
import hello.newsfeed.auth.dto.SignResponse;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        // 사용 중인 이메일 예외처리 (탈퇴x)
        if (userRepository.existsByEmailAndDeletedFalse(authRequest.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        // 이미 탈퇴한 이메일 예외처리 (재가입 불가)
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new IllegalArgumentException("이미 탈퇴한 이메일입니다. 재가입할 수 없습니다.");
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(authRequest.getPassword());
        User user = new User(
                authRequest.getName(),
                authRequest.getEmail(),
                encodedPassword
        );
        userRepository.save(user);
        return new SignResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    // 로그인 //
    @Transactional(readOnly = true)
    public SignResponse login(AuthRequest authRequest) {

        // 이메일로 유저 조회 (탈퇴 포함)
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "이메일이 틀렸습니다.")
                );

        // 탈퇴 여부 확인
        if (user.isDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "이미 탈퇴한 계정입니다."
            );
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."
            );
        }

        // 로그인 성공
        return new SignResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    // 회원 탈퇴 //
    @Transactional
    public void deleteMe(Long userId, String password) {
        // 사용자 조회 -> 계정 존재 여부 확인(비밀번호 검증시 필요)
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 계정입니다.")
        );

        // 탈퇴 계정 확인
        if (user.isDeleted()) {
            throw  new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "이미 탈퇴한 계정입니다."
            );
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."
            );
        }

        // 탈퇴
        user.softDeleted();
    }
}
