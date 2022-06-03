package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.*;
import jpa.shoppingmall.service.CartService;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.service.MemberService;
import jpa.shoppingmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final CartService cartService;
    @GetMapping("/order")
    public String orderForm(Model m, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");
        Long cartId = (Long) session.getAttribute("cartId");
//        List<Member> members = memberService.findAll();
//        List<Item> items = itemService.findAll();
        List<CartItem> items = cartService.findAll(cartId);


        m.addAttribute("members", member);
        m.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model m){
        List<Order> orders = orderService.findOrders(orderSearch);
        m.addAttribute("orders", orders);
//        log.info("name ={}, status ={}", orderSearch.getMemberName(), orderSearch.getOrderStatus());
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
