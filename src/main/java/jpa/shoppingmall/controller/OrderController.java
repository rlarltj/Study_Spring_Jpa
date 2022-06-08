package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.*;
import jpa.shoppingmall.exception.NotEnoughStockException;
import jpa.shoppingmall.service.CartService;
import jpa.shoppingmall.service.ItemService;
import jpa.shoppingmall.service.MemberService;
import jpa.shoppingmall.service.OrderService;
import jpa.shoppingmall.web.OrderForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        OrderFormSetting(m, request);
        OrderForm orderForm = new OrderForm();
        m.addAttribute("orderForm", orderForm);
        return "order/orderFormV2";
    }

    private void OrderFormSetting(Model m, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");
        Long cartId = (Long) session.getAttribute("cartId");
        List<CartItem> items = cartService.findAll(cartId);

        m.addAttribute("members", member);
        m.addAttribute("items", items);
    }

    @PostMapping("/order")
    public String order(@Valid @ModelAttribute OrderForm form, BindingResult bindingResult, Model m,
                        HttpServletRequest request, RedirectAttributes rattr){
        if(bindingResult.hasErrors()){
            OrderFormSetting(m, request);
            log.info("주문 에러={}", bindingResult.getFieldErrors());
            return "order/orderFormV2";
        }
        try{
            log.info("멤버 아이디={}, 아이템 ={}, 수량={}", form.getMemberId(), form.getItemId(), form.getCount());
            orderService.order(form.getMemberId(), form.getItemId(), form.getCount());
        }catch(NotEnoughStockException ex){
            rattr.addFlashAttribute("msg", "주문 가능 수량 초과");
            log.info("재고 부족",ex);
            return "redirect:/order";
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model m){
        List<Order> orders = orderService.findOrders(orderSearch);
        m.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
