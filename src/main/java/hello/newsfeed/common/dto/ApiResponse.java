package hello.newsfeed.common.dto;

public record ApiResponse<T>(
        String message,
        int status,
        T data
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("", 200, data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(message, 200, data);
    }
}

