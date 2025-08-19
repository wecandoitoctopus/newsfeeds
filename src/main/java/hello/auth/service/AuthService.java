package hello.auth.service;

import hello.auth.dto.AuthRequest;
import hello.auth.dto.AuthResponse;
import hello.auth.dto.SignResponse;
import hello.common.config.PasswordEncoder;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 중복 이메일 예외처리 //
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }
        // 이메일 형식 예외처리 //
        if (authRequest.getEmail() == null || !authRequest.getEmail().matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
        // 비밀번호 형식 예외처리 //
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

}
