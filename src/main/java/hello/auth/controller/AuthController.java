package hello.auth.controller;

import hello.auth.dto.AuthRequest;
import hello.auth.dto.AuthResponse;
import hello.auth.dto.SignResponse;
import hello.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 //
    @PostMapping("/auth/signup")
    public ResponseEntity<AuthResponse<SignResponse>> signup(
            @RequestBody AuthRequest authRequest) {
        SignResponse result = authService.save(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthResponse.success("회원가입 성공", result));
    }

}
