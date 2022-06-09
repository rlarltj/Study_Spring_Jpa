package jpa.shoppingmall.repository;

import jpa.shoppingmall.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public List<Item> findPaging(int offset, int limit) {
        return em.createQuery("select i from Item i", Item.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Item> findByKeyword(String name, int offset, int limit){
        return em.createQuery("select i from Item i where i.name like :name", Item.class)
                .setParameter("name", "%"+name+"%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public Long getTotalCount(){
        return em.createQuery("select count(i) from Item i", Long.class)
                .getSingleResult();
    }
}
