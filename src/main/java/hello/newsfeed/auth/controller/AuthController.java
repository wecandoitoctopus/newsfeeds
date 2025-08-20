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

}
