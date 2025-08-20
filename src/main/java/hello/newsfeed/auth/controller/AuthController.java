package hello.newsfeed.auth.controller;

import hello.newsfeed.auth.dto.AuthRequest;
import hello.newsfeed.auth.dto.AuthResponse;
import hello.newsfeed.auth.dto.SignResponse;
import hello.newsfeed.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 //
    @PostMapping("/auth/signup")
    public ResponseEntity<AuthResponse<SignResponse>> signup(
            @Valid @RequestBody AuthRequest authRequest) {
        SignResponse result = authService.save(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.success("회원가입 성공", result));
    }

    // 로그인 //
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse<SignResponse>> login(
            @RequestBody AuthRequest authRequest,
            HttpServletRequest request
    ) {
        SignResponse result = authService.login(authRequest);

        // Cookie Session 발급 //
        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", result.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.success("로그인 성공", result));
    }

    // 회원탈퇴 //
    @DeleteMapping("/auth/me")
    public ResponseEntity<AuthResponse<Void>> deleteMe(
            HttpServletRequest request,
            @RequestParam String password
    ) {
        // 세션에서 유저 아이디 가져오기
        HttpSession session = request.getSession(false); // false -> 세션 없으면 null 반환
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        // 타입변환(Long) 표시 필요 -> session.getAttribute(String) = 원래 리턴 타입 object
        Long userId = (Long) session.getAttribute("LOGIN_USER");

        // 서비스 호출
        authService.deleteMe(userId, password);

        return ResponseEntity.ok(
                AuthResponse.success("회원탈퇴 성공",null)
        );
    }
}
