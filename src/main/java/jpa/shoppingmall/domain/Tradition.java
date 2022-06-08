package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter @Setter
@DiscriminatorValue("T")
public class Tradition extends Item {
    private String location;
    private String artist;

    public Tradition() {
    }

    public Tradition(String name, int price, int stockQuantity, String artist, String location, String seller) {
        super(name, price, stockQuantity, seller);
        this.artist = artist;
        this.location = location;
    }
}
