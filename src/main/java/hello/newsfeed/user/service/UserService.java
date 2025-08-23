package hello.newsfeed.user.service;

import hello.newsfeed.user.dto.*;
import hello.newsfeed.user.entity.User;
import hello.newsfeed.user.repository.UserRepository;
import hello.newsfeed.common.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    public UserResponse getMe(Long userId) {
        User u = users.findById(userId).orElseThrow(() -> new BadRequestException("USER_NOT_FOUND"));
        return toPrivate(u);
    }

    public PublicUserResponse getPublicByUsername(String username) {
        User u = users.findByUsername(username).orElseThrow(() -> new BadRequestException("USER_NOT_FOUND"));
        return toPublic(u);
    }

    @Transactional
    public UserResponse patchMe(Long userId, UpdateUserPatchRequest req) {
        User u = users.findById(userId).orElseThrow(() -> new BadRequestException("USER_NOT_FOUND"));
        u.changeUsername(req.username());
        return toPrivate(u);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordPatchRequest req) {
        User u = users.findById(userId).orElseThrow(() -> new BadRequestException("USER_NOT_FOUND"));
        // -> 값이 없는 경우

        if (!encoder.matches(req.currentPassword(), u.getPassword())) throw new BadRequestException("CURRENT_PASSWORD_MISMATCH");
        // --> 현재 비밀번호를 틀린 경우, 필수 구현 비밀번호 예외 처리 1번

        if (encoder.matches(req.newPassword(), u.getPassword())) throw new BadRequestException("PASSWORD_SAME_AS_CURRENT");
        String encoded = encoder.encode(req.newPassword());
        u.changePassword(encoded);
        // --> 현재 비밀번호와 교체할 비밀번호가 같은 경우, 필수 구현 비밀번호 예외 처리 3번
    }

    @Transactional
    public UserResponse updateProfileImage(Long userId, UpdateProfileImagePatchRequest req) {
        User u = users.findById(userId).orElseThrow(() -> new BadRequestException("USER_NOT_FOUND"));
        String v = (req.profileImage()==null || req.profileImage().isBlank()) ? null : req.profileImage();
        u.changeProfileImage(v);
        return toPrivate(u);
    }

    private UserResponse toPrivate(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getUsername(), u.getProfileImage(), u.getCreatedAt(), u.getCreatedAt());
    }

    private PublicUserResponse toPublic(User u){
        return new PublicUserResponse(u.getId(), u.getUsername(), u.getProfileImage(), u.getCreatedAt(), u.getCreatedAt());
    }
}