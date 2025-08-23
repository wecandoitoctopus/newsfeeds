

package hello.newsfeed.common.exception;

import hello.newsfeed.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    private ResponseEntity<ApiResponse<Void>> body(HttpStatus status, String msg) {
        ApiResponse<Void> wrapper = new ApiResponse<>(msg, status.value(), null);
        return ResponseEntity.status(status).body(wrapper);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException e) {
        return body(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> illegalArg(IllegalArgumentException e) {
        return body(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> unauthorized() {
        return body(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> inValid() {
        return body(HttpStatus.BAD_REQUEST, "Not Found");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> fallback(Exception e, HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
        return body(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER ERROR");
    }
}