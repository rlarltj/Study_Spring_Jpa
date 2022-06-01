package jpa.shoppingmall.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("로그인 체크 인터셉터 {}", requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("name") == null){
            log.info("미 인증 사용자입니다.");
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
