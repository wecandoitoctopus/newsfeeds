package hello.newsfeed.user.controller;

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
    public ResponseEntity<UserResponse> me(@SessionAttribute("LOGIN_USER") Long userId) {
        return ResponseEntity.ok(svc.getMe(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicUserResponse> getPublic(@PathVariable Long id){
        return ResponseEntity.ok(svc.getPublic(id));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> patchMe(@SessionAttribute("LOGIN_USER") Long userId,
                                                @RequestBody @Valid UpdateUserPatchRequest req) {
        return ResponseEntity.ok(svc.updateMe(userId, req));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> patchPassword(@SessionAttribute("LOGIN_USER") Long userId,
                                              @RequestBody @Valid ChangePasswordPatchRequest req) {
        svc.changePassword(userId, req);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/profile/image")
    public ResponseEntity<UserResponse> patchProfileImage(@SessionAttribute("LOGIN_USER") Long userId,
                                                          @RequestBody @Valid UpdateProfileImagePatchRequest req) {
        return ResponseEntity.ok(svc.updateProfileImage(userId, req));
    }
}
