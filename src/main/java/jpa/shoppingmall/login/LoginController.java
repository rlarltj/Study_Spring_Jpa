package jpa.shoppingmall.login;

import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.exception.NoSuchUserException;
import jpa.shoppingmall.repository.MemberRepository;
import jpa.shoppingmall.service.MemberService;
import jpa.shoppingmall.session.SessionConst;
import jpa.shoppingmall.web.LoginForm;
import jpa.shoppingmall.web.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginForm(Model m) {
        m.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        /**
         * findMember가 null이면 Exception 발생
         * findMember의 비밀번호와 입력한 비밀번호가 같으면 로그인 성공
         * findMember의 비밀번호와 입력한 비밀번호가 다르면 로그인 실패
         */

        Member findMember = memberRepository.findMemberByLoginId(loginForm.getId())
                .orElseThrow(() -> new NoSuchUserException("일치하는 아이디가 없습니다."));

        String password = findMember.getPassword();

        if(!loginForm.getPassword().equals(password)){
            return "login/loginForm";

        }else{
            HttpSession session = request.getSession();
            session.setAttribute("member", findMember);

            return "redirect:/";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null)
        session.invalidate();

        return "redirect:/";
    }

}
