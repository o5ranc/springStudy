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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @OneToMany(mappedBy = "order") // (실제 테이블명 'orders'클래스명을 소문자로 줬다고 생각
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    //private LocalDateTime regTime; // 등록시간 => 자동변경으로 추후 변경함

    //private LocalDateTime updateTime; // 수정시간 => 자동변경으로 추후 변경함
}
