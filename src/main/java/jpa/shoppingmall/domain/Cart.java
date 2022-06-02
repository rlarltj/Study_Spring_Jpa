package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Cart {
    @Id @GeneratedValue
    @Column(name = "CART_ID")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private Member member;

    private LocalDateTime cartDate;

    //== 연관관계 ==//
    public void setMember(Member member) {
        this.member = member;
        member.setCart(this);
    }

    public void setCartItem(CartItem cartItem){
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
    }
    public static Cart createCart(Member member, CartItem ... cartItems){
        Cart cart = new Cart();
        cart.setMember(member);

        for(CartItem c : cartItems){
            cart.setCartItem(c);
        }

        cart.setCartDate(LocalDateTime.now());

        return cart;
    }

    public static Cart createCartV2(Cart cart, Member member, CartItem ... cartItems){
//        cart.setMember(member);

        for(CartItem c : cartItems){
            cart.setCartItem(c);
        }

        cart.setCartDate(LocalDateTime.now());

        return cart;
    }
}
