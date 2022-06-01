package jpa.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Cart {
    @Id @GeneratedValue
    @Column(name = "CART_ID")
    private Long id;


}
