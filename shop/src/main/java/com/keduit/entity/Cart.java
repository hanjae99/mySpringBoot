package com.keduit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Cart extends BaseEntity{

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 일대일 매핑
    @JoinColumn(name = "member_id") //join 연산
    private Member member;

    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);

        return cart;
    }
}
