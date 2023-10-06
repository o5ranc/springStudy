package com.keduit.dto;

import com.keduit.constant.OrderStatus;
import com.keduit.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDTO {

    public OrderHistDTO(Order order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태


    private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

    //주문 상품리스트
    public void addOrderItemDto(OrderItemDTO orderItemDTO){
        orderItemDTOList.add(orderItemDTO);
    }
}