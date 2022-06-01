//package jpa.shoppingmall.interceptor;
//
//import jpa.shoppingmall.domain.Member;
//import jpa.shoppingmall.repository.MemberRepository;
//import jpa.shoppingmall.session.SessionConst;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@RequiredArgsConstructor
//public class AdminInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        Member member = (Member) session.getAttribute("member");
//        //세션에 멤버 객체를 저장하자
//
//        //멤버의 등급이 ADMIN인 경우, 아닌 경우
//    }
//}
