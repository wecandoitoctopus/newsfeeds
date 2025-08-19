package hello.newsfeed.user.controller;

import hello.newsfeed.user.dto.*;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository users;

// httpie 테스트용
//    @PostMapping
//    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserRequest req) {
//        if (users.existsByEmail(req.email())) return ResponseEntity.status(409).<Void>build();
//        User u = users.save(User.builder()
//                .email(req.email())
//                .username(req.username())
//                .password(req.password())
//                .build());
//        return ResponseEntity.created(URI.create("/users/" + u.getId())).<Void>build();
//    }


    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@RequestHeader("X-USER-ID") Long userId) {
        return users.findById(userId)
                .map(u -> ResponseEntity.ok(toDto(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> patchMe(@RequestHeader("X-USER-ID") Long userId,
                                                @RequestBody @Valid UpdateUserPatchRequest req) {
        return users.findById(userId).map(u -> {
            u.changeUsername(req.username());
            return ResponseEntity.ok(toDto(users.save(u)));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> patchPassword(@RequestHeader("X-USER-ID") Long userId,
                                              @RequestBody @Valid ChangePasswordPatchRequest req) {
        return users.findById(userId).map(u -> {
            u.changePassword(req.newPassword());
            users.save(u);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().<Void>build());
    }

    @PatchMapping("/me/profile/image")
    public ResponseEntity<UserResponse> patchProfileImage(@RequestHeader("X-USER-ID") Long userId,
                                                          @RequestBody @Valid UpdateProfileImagePatchRequest req) {
        return users.findById(userId).map(u -> {
            String v = (req.profileImage() == null || req.profileImage().isBlank()) ? null : req.profileImage();
            u.changeProfileImage(v);
            return ResponseEntity.ok(toDto(users.save(u)));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private UserResponse toDto(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getUsername(), u.getProfileImage());
    }
}
