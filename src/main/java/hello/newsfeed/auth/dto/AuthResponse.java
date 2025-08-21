package hello.newsfeed.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
// 공통 응답 //
public class AuthResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String errorCode;

    // 성공 응답 생성 //
    public static <T> AuthResponse<T> success(String message, T data) {
        return new AuthResponse<>(true, message, data, null);
    }

    // 실패 응답 생성 //
    public static <T> AuthResponse<T> error(String errorCode, String message) {
        return new AuthResponse<>(false, message, null, errorCode);
    }
}
