package com.keduit.entity;

import com.keduit.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //한명의 회원은 여러 주문 가능 -> 주문입장에서 다대일관계
    @JoinColumn(name = "member_id")
    private Member member;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //하나의 주문이 여러 개의 주문상품을 가지므로 List 자료형을 사용해서 매핑
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태
}
