package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;

    public Book() {
    }

    public Book(String name, int price, int stockQuantity, String author, String isbn, String seller) {
        super(name, price, stockQuantity, seller);
        this.author = author;
        this.isbn = isbn;
    }
}
