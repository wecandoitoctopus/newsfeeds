package hello.newsfeed.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)    // NULL 값 제외
// 공통 응답 //
public class AuthResponse<T> {                // <> 제네릭 타입 -> 뭐든 들어 올 수 있음

    private boolean success;                  // 성공,실패 여부 -> true , false
    private String message;                   // 응답 메세지
    private T data;                           // 실제 데이터
    private String errorCode;                 // 에러 코드 (실패시)

    // 성공 응답 생성 // - login, signup, deleteMe 적용 완료
    public static <T> AuthResponse<T> success(String message, T data) {
        return new AuthResponse<>(true, message, data, null);
    }

    // 실패 응답 생성 //
    public static <T> AuthResponse<T> error(String errorCode, String message) {
        return new AuthResponse<>(false, message, null, errorCode);
    }
}
