package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter @Setter
@DiscriminatorValue("L")
public class Lp extends Item {
    private String artist;
    private String etc;

    public Lp() {
    }

    public Lp(String name, int price, int stockQuantity, String artist, String etc, String seller) {
        super(name, price, stockQuantity, seller);
        this.artist = artist;
        this.etc = etc;
    }
}
