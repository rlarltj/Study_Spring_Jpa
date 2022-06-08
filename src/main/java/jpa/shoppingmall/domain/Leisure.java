package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter
@Setter
@DiscriminatorValue("L")
public class Leisure extends Item{
    private String location; //장소
    private int time; //소요 시간

    public Leisure() {
    }

    public Leisure(String name, int price, int stockQuantity, String location, int time, String seller) {
        super(name, price, stockQuantity, seller);
        this.location = location;
        this.time = time;
    }
}
