package com.keduit.service;

import com.keduit.constant.ItemSellStatus;
import com.keduit.constant.OrderStatus;
import com.keduit.dto.OrderDTO;
import com.keduit.entity.Item;
import com.keduit.entity.Member;
import com.keduit.entity.Order;
import com.keduit.entity.OrderItem;
import com.keduit.repository.ItemRepository;
import com.keduit.repository.MemberRepository;
import com.keduit.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem() {
        Item item = new Item();
        item.setItemNm("test");
        item.setItemDetail("test detail");
        item.setPrice(3000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public Member saveMember() {
        // 현재 이메일 정보만 필요
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문 테스트")
    public void orderTest() {
        Item item = saveItem();
        Member member = saveMember();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setItemId(item.getId());
        orderDTO.setCount(10);
        Long orderId = orderService.order(orderDTO, member.getEmail());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();
        int totalPrice = orderDTO.getCount() * item.getPrice();
        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrderTest() {
        Item item = saveItem();
        Member member = saveMember();
        System.out.println("item.getId() === " + item.getId());
        OrderDTO orderDTO = new OrderDTO(); // 테스트를 위한 주문데이터 생성
        System.out.println("orderDTO.getItemId() = " + orderDTO.getItemId());
        orderDTO.setCount(10);
        orderDTO.setItemId(item.getId());

        Long orderId = orderService.order(orderDTO, member.getEmail()); // 주문

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId); // 주문건 취소

        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        assertEquals(100, item.getStockNumber());
    }
}
