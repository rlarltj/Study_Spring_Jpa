package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CartItem {
    @Id @GeneratedValue
    @Column(name = "CART_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int cartCount;
    private int cartPrice;

    public static CartItem createCartItem(Item item){
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);

        return cartItem;
    }
}
