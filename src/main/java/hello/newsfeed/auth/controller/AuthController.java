package hello.newsfeed.auth.controller;

import hello.newsfeed.auth.dto.AuthRequest;
import hello.newsfeed.auth.dto.AuthResponse;
import hello.newsfeed.auth.dto.SignResponse;
import hello.newsfeed.auth.service.AuthService;
import hello.newsfeed.common.dto.ResponseMessage;
import hello.newsfeed.common.constant.SessionConstant;
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
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입 //
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse<SignResponse>> signup(
            @Valid @RequestBody AuthRequest authRequest
    ) {
        SignResponse result = authService.save(authRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.success(
                        ResponseMessage.SUCCESS_SIGNUP.getMessage(), result)
                );
    }

    // 로그인 //
    @PostMapping("/login")
    public ResponseEntity<AuthResponse<SignResponse>> login(
            @RequestBody AuthRequest authRequest,
            HttpServletRequest request
    ) {
        SignResponse result = authService.login(authRequest);

        // Cookie Session 발급 //
        HttpSession session = request.getSession();
        session.setAttribute(
                SessionConstant.LOGIN_USER, result.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.success(
                        ResponseMessage.SUCCESS_LOGIN.getMessage(), result));
    }

    // 회원탈퇴 //
    @DeleteMapping("/me")
    public ResponseEntity<AuthResponse<Void>> deleteMe(
            HttpServletRequest request,
            @RequestParam String password
    ) {
        // 세션에서 유저 아이디 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConstant.LOGIN_USER) == null
        ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute(SessionConstant.LOGIN_USER);

        // 서비스 호출
        authService.deleteMe(userId, password);

        return ResponseEntity.ok(
                AuthResponse.success(ResponseMessage.SUCCESS_DELETEME.getMessage(),null)
        );
    }
}
