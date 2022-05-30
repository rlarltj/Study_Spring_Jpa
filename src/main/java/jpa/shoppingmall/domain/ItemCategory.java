package jpa.shoppingmall.domain;

import javax.persistence.*;

@Entity
@Table(name="ITEM_CATEGORY")
public class ItemCategory {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_CATEGORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
}
