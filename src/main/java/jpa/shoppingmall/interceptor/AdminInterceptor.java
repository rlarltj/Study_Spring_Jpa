package jpa.shoppingmall.interceptor;

import jpa.shoppingmall.domain.GRADE;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.MemberRepository;
import jpa.shoppingmall.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        //세션에 멤버 객체를 저장하자

        if(member.getGrade() == GRADE.GUEST){
            request.setAttribute("msg", "no permission");
            log.info("권한 없는 사용자 msg ={}", request.getAttribute("msg"));
            response.sendRedirect("/");
            return false;
        }
        //멤버의 등급이 ADMIN이면 true
        return true;
    }
}
