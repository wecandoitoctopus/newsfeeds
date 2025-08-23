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

    private static final String[] WHITE_LIST = {
            "/", "/auth/signup", "/auth/login",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/auth/**"
    };

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();

        boolean whitelisted = isWhiteList(requestURI);
        log.info("LoginFilter uri={}, whitelisted={}", requestURI, whitelisted);

        if (whitelisted || isPublicProfileGet(httpRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!isWhiteList(requestURI)) {                                                 // 화이트리스트 외 요청이면
            HttpSession session = httpRequest.getSession(false);                     // 세션 조회 (없으면 null)

            // 로그인하지 않은 사용자인 경우
            if (session == null || session.getAttribute("LOGIN_USER") == null) {     // 세션 키값
                // 세션은 일종의 키/벨류
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 해주세요.");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);

    }

    //GET /users/{username} 허용, /users/me 제외
    private boolean isPublicProfileGet(HttpServletRequest req) {
        if (!"GET".equals(req.getMethod())) return false;
        String uri = req.getRequestURI();
        if ("/users/me".equals(uri)) return false;
        return uri.startsWith("/users/") && uri.indexOf('/', "/users/".length()) == -1;
    }

}

