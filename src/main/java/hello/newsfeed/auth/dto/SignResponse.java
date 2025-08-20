package hello.newsfeed.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)    // NULL 값 제외
// <T> -> 실제 데이터
public class SignResponse {

    private final Long id;
    private final String name;
    private final String email;
}
