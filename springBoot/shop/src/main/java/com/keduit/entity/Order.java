package com.keduit.entity;

import com.keduit.constant.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
                        orphanRemoval = true) // (실제 테이블명 'orders'클래스명을 소문자로 줬다고 생각
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    //private LocalDateTime regTime; // 등록시간 => 자동변경으로 추후 변경함

    //private LocalDateTime updateTime; // 수정시간 => 자동변경으로 추후 변경함

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); // 주문할 아이템들을 저장하는 List<OrderItem> orderItems 에다가 추가
        orderItem.setOrder(this); // orderItem 쪽에서도 order에 대한 정보를 저장하고 있음
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        // order 정보를 구성해서 리턴
        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); // 각 아이템의 가격에서 총 주문상품 금액 계산함
        }
        return totalPrice;
    }
}
