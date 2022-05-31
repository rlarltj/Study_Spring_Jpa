package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.service.MemberService;
import jpa.shoppingmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String orderForm(Model m) {
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findAll();

        m.addAttribute("members", members);
        m.addAttribute("items", items);

        return "order/orderForm";
    }
}
