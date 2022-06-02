package jpa.shoppingmall.repository;

import jpa.shoppingmall.domain.Cart;
import jpa.shoppingmall.domain.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
public class CartRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Cart cart){
        em.persist(cart);
    }

    public Cart findOne(Long cartId){
        return em.find(Cart.class, cartId);
    }
    public CartItem findCartItem(Long cartItemId){
        return em.find(CartItem.class, cartItemId);
    }

    public List<CartItem> findAll(Long cartId) {
        log.info("repository - cartId={}", cartId);
        return em.createQuery("select c from CartItem c where c.cart.id = :cartId", CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }
    public void addCartItem(CartItem item){
        em.persist(item);
    }


    public void deleteOne(CartItem item){
        em.remove(item);
    }
}
