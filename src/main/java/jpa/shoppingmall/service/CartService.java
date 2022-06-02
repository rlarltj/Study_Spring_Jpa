package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Cart;
import jpa.shoppingmall.domain.CartItem;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.CartRepository;
import jpa.shoppingmall.repository.ItemRepository;
import jpa.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public List<CartItem> findAll(Long cartId) {
        return cartRepository.findAll(cartId);
    }

    @Transactional
    public void deleteOne(Long cartItemId){
        CartItem item = cartRepository.findCartItem(cartItemId);
        cartRepository.deleteOne(item);
    }

    @Transactional
    public Long addCartItem(Long cartId, Long memberId, Long itemId){
        Member member = memberRepository.findOne(memberId);
        log.info("member={}", member);
        Item item = itemRepository.findOne(itemId);
        Cart cart = cartRepository.findOne(cartId);
        CartItem cartItem = CartItem.createCartItem(item);

        Cart.createCartV2(cart, member, cartItem);
        cartRepository.save(cart);
//        Long cartId = createCart(memberId, itemId);
        return cart.getId();
    }

    @Transactional
    public Long createCart(Long memberId, Long itemId){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        CartItem cartItem = CartItem.createCartItem(item);
        Cart cart = Cart.createCart(member, cartItem);
        cartRepository.save(cart);
        //장바구니가 계속 생성된다.
        return cart.getId();
    }

    @Transactional
    public Long firstCreateCart(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        Cart cart = new Cart();
        cartRepository.save(cart);

        return cart.getId();
    }
}
