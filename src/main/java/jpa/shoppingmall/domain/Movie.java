package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Getter
@Setter
@DiscriminatorValue("M")
public class Movie extends Item{
    private String director;
    private String actor;

    public Movie() {
    }

    public Movie(String name, int price, int stockQuantity, String director, String actor, String seller) {
        super(name, price, stockQuantity, seller);
        this.director = director;
        this.actor = actor;
    }
}
