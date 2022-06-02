package jpa.shoppingmall.controller;

import jpa.shoppingmall.domain.CartItem;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.service.CartService;
import jpa.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;

    @GetMapping("/carts")
    public String cartList(Model m, HttpServletRequest request){
        HttpSession session = request.getSession();
//        Member member = (Member)session.getAttribute("member");
        Long cartId = (Long) session.getAttribute("cartId");
        log.info("카트 id = {}", cartId);
        List<CartItem> items = cartService.findAll(cartId);
        m.addAttribute("items", items);

        return "cart/cartList";
    }

    @PostMapping("/cart/{itemId}")
    public String addCart(@PathVariable Long itemId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");
        Item item = itemService.findOne(itemId);
        Long cartId = (Long) session.getAttribute("cartId");

        if(cartId == null){

            cartId = cartService.createCart(member.getId(), item.getId());
            session.setAttribute("cartId", cartId);

        }else{
            log.info("cartController- member={}, cartId={}", member, cartId);
            cartService.addCartItem(cartId, member.getId(), item.getId());

        }
        //카트 Id로 카트를 찾아온다.
        return "redirect:/items";
    }

    @PostMapping("/cart/{itemId}/delete")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");
        cartService.deleteOne(itemId);
        log.info("item ={}", itemId);
        return "redirect:/";
    }
}
