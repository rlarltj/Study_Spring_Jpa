package jpa.shoppingmall.repository;

import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.ItemCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class CategoryRepository {
    @PersistenceContext
    EntityManager em;

    public void save(ItemCategory itemCategory) {
        em.persist(itemCategory);
    }

    public ItemCategory findOne(Long itemId){
        return (ItemCategory) em.createQuery("select itemcate from ItemCategory itemcate " +
                "where itemcate.item.id =:itemId")
                .setParameter("itemId", itemId)
                .getSingleResult();
    }

    public List<Item> findCateItems(String category){
        return em.createQuery("select item from ItemCategory itemcate " +
                        "join itemcate.item as item " +
                        "where itemcate.category.name = :category")
                .setParameter("category", category)
                .getResultList();
    }

    public Long getTotalCount(String category) {
        return em.createQuery("select count(c) from Category c where c.name =: category", Long.class)
                .setParameter("category", category)
                .getSingleResult();
    }
}
