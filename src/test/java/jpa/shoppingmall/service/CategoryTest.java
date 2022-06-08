package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Specialty;
import jpa.shoppingmall.domain.Category;
import jpa.shoppingmall.domain.Item;
import jpa.shoppingmall.domain.ItemCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@SpringBootTest
public class CategoryTest {
    @Autowired ItemService itemService;
    @Autowired OrderService orderService;
    @Autowired CategoryService categoryService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void category1() {
        Specialty specialty = new Specialty("책책책", 10000, 5, "권군", "isbn", "고려서점");
        em.persist(specialty);
        String c = "book";
        Category cate = Category.createCategory(c);
        em.persist(cate);

        ItemCategory itemCategory = ItemCategory.createCategoryItem(cate, specialty);
        em.persist(itemCategory);


        List resultList = em.createQuery("select item from ItemCategory itemcate " +
                        "join itemcate.item as item " +
                        "where itemcate.category.id = :category")
                .setParameter("category", cate.getId())
                .getResultList();
        Specialty specialty1 = (Specialty) resultList.get(0);
        Assertions.assertThat(specialty1.getName()).isEqualTo(specialty.getName());

        List resultList2 = em.createQuery("select item from ItemCategory itemcate " +
                "join itemcate.item as item " +
                "where itemcate.category.name = :category")
                        .setParameter("category", "도서")
                                .getResultList();
        System.out.println("size = "+ resultList2.size());
    }

    @Test
    public void cateService() {
        Specialty specialty = new Specialty("책책책", 10000, 5, "권군", "isbn", "고려서점");
        em.persist(specialty);

        categoryService.save(specialty.getId(), "책코너");

        ItemCategory one = categoryService.findOne(specialty.getId());

        System.out.println(one.getCategory().getName());
    }

    @Test
    public void cateItemFindTest() {
        List<Item> items = categoryService.findItems("도서");
        System.out.println(items.size());
    }
}
