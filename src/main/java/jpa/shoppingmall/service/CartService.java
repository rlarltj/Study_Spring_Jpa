package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Cart;
import jpa.shoppingmall.domain.CartItem;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.CartRepository;
import jpa.shoppingmall.repository.ItemRepository;
import jpa.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long createCart(Long memberId, Long itemId){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        CartItem cartItem = CartItem.createCartItem(item);
        Cart cart = Cart.createCart(member, cartItem);
        cartRepository.save(cart);

        return cart.getId();
    }

}
