package jpa.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    
    private String username;
    private int age;
    private int money;

    @OneToMany(mappedBy = "member")
    private List<Order> order = new ArrayList<>();


    @Embedded
    private Address address;


    public Member() {
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
