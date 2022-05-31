package jpa.shoppingmall.login;

import jpa.shoppingmall.web.LoginForm;
import jpa.shoppingmall.web.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(Model m) {
        m.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }
}
