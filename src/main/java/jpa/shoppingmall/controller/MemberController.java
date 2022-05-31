package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.Address;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.service.MemberService;
import jpa.shoppingmall.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/new")
    public String showAddForm(Model m) {
        MemberForm memberForm = new MemberForm();
        m.addAttribute("memberForm", memberForm);

        return "members/createMemberForm";
    }

    @GetMapping
    public String memberList(Model m) {
        List<Member> members = memberService.findAll();
        m.addAttribute("members", members);

        return "members/memberList";
    }

    @PostMapping("/new")
    public String saveAddForm(@Valid MemberForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        member.setUsername(form.getName());
        member.setAddress(new Address(form.getCity(), form.getStreet(), form.getZipcode()));

        memberService.join(member);
        return "redirect:/";
    }
}
