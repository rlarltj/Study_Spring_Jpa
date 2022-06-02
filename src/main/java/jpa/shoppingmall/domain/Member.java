package jpa.shoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private int age;
    private int money;

    @Enumerated(EnumType.STRING)
    private GRADE grade;

    @OneToMany(mappedBy = "member")
    private List<Order> order = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Embedded
    private Address address;


    public Member() {
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
