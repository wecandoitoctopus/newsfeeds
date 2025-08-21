package hello.newsfeed.user.controller;

import hello.newsfeed.common.dto.ApiResponse;
import hello.newsfeed.user.dto.*;
import hello.newsfeed.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService svc;

    public UserController(UserService svc) {
        this.svc = svc;
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(@SessionAttribute("LOGIN_USER") Long userId) {
        UserResponse dto = svc.getMe(userId);
        return ApiResponse.ok("me GET 호출 성공", dto);
    }

    @GetMapping("/{username}")
    public ApiResponse<PublicUserResponse> getPublic(@PathVariable String username){
        PublicUserResponse dto = svc.getPublicByUsername(username);
        return ApiResponse.ok("username GET 호출 성공", dto);
    }

    @PatchMapping("/me")
    public ApiResponse<UserResponse> patchMe(@SessionAttribute("LOGIN_USER") Long userId,
                                                @RequestBody @Valid UpdateUserPatchRequest req) {
        UserResponse dto = svc.patchMe(userId, req);
        return ApiResponse.ok("me PATCH 호출 성공", dto);
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> patchPassword(@SessionAttribute("LOGIN_USER") Long userId,
                                                        @RequestBody @Valid ChangePasswordPatchRequest req) {
        svc.changePassword(userId, req);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/profile/image")
    public ApiResponse<UserResponse> patchProfileImage(@SessionAttribute("LOGIN_USER") Long userId,
                                                          @RequestBody @Valid UpdateProfileImagePatchRequest req) {
        UserResponse dto = svc.updateProfileImage(userId, req);
        return ApiResponse.ok("profileImage PATCH 호출 성공", dto);
    }
}
