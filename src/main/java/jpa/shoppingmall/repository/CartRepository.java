package jpa.shoppingmall.repository;

import jpa.shoppingmall.domain.Cart;
import jpa.shoppingmall.domain.CartItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CartRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Cart cart){
        em.persist(cart);
    }

    public CartItem findOne(Long cartItemId){
        return em.find(CartItem.class, cartItemId);
    }

    public List<CartItem> findAll() {
        return em.createQuery("select c from CartItem c", CartItem.class)
                .getResultList();
    }

    public void deleteOne(Long cartItemid){
        CartItem one = findOne(cartItemid);
        em.remove(one);
    }
}
