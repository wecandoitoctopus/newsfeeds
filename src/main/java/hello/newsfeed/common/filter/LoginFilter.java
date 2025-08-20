package hello.newsfeed.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/auth/signup", "/auth/login"};

    @Override
    public void doFilter(
            ServletRequest servletRequest,       // 서블릿 요청
            ServletResponse servletResponse,     // 서블릿 응답
            FilterChain filterChain              // 다음 필터/서블릿 호출 체인
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;           // HTTP 요청으로 다운캐스팅
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();                                // 요청 URI 추출

        if (!isWhiteList(requestURI)) {                                                 // 화이트리스트 외 요청이면
            HttpSession session = httpRequest.getSession(false);                     // 세션 조회 (없으면 null)

            // 로그인하지 않은 사용자인 경우
            if (session == null || session.getAttribute("LOGIN_USER") == null) { // 세션 키값 = LOGIN_DIRECTOR  -> login user
                // 세션은 일종의 키/벨류
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 해주세요.");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);                          // 다음 단계로 전달
    }
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);                   // 경로 매칭
    }
}

