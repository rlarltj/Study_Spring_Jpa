package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.domain.Order;
import jpa.shoppingmall.domain.OrderSearch;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.service.MemberService;
import jpa.shoppingmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
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
