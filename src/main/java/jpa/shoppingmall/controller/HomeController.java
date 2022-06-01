package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.exception.NoSuchUserException;
import jpa.shoppingmall.repository.MemberRepository;
import jpa.shoppingmall.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model m) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        String loginId = (String) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member member = memberRepository.findMemberByLoginId(loginId).get();
//                .orElseThrow(() -> new NoSuchUserException("일치하는 멤버가 없습니다."));

        if (member == null) {
            return "home";
        }

        m.addAttribute("member", member);
        return "login/loginHome";
    }
}
