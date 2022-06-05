package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ITEM_CATEGORY")
@Getter @Setter
public class ItemCategory {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_CATEGORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public void setItem(Item item) {
        this.item = item;
        item.getItemCategory().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getItemCategory().add(this);
    }
    public static ItemCategory createCategoryItem(Category category, Item item) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setItem(item);
        itemCategory.setCategory(category);

        return itemCategory;
    }
}
