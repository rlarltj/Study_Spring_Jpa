package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("S")
@Getter @Setter
public class Specialty extends Item {
    private String origin; // 생산지
    private String prodGroup; // 생산단체

    public Specialty() {
    }

    public Specialty(String name, int price, int stockQuantity, String origin, String prodGroup, String seller) {
        super(name, price, stockQuantity, seller);
        this.origin = origin;
        this.prodGroup = prodGroup;
    }
}
