package hello.newsfeed.common.dto;

// 성공 응답 메세지 별도 관리
public enum ResponseMessage {
    SUCCESS_LOGIN("로그인 성공", 201),
    SUCCESS_SIGNUP("회원가입 성공", 201),
    SUCCESS_DELETEME("회원탈퇴 성공", 200);

    private final String message;
    private final int code;

    ResponseMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}