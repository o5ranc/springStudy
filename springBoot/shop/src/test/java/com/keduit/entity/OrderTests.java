package com.keduit.entity;

import com.keduit.constant.ItemSellStatus;
import com.keduit.repository.ItemRepository;
import com.keduit.repository.MemberRepository;
import com.keduit.repository.OrderItemRepository;
import com.keduit.repository.OrderRepository;
import net.bytebuddy.asm.Advice;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderTests {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemNm("test");
        item.setItemDetail("test detail");
        item.setPrice(3000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(10000);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Order order = new Order();
        for(int i=0; i<3; i++){
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(50000);
            orderItem.setCount(10);
            orderItem.setOrder(order);
            orderItem.setRegTime(LocalDateTime.now());
            orderItem.setUpdateTime(LocalDateTime.now());
            order.getOrderItems().add(orderItem);
        }
        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                //orElseThrow == findById 메서드의 결과가 비어 있을 경우 (주문을 찾을 수 없는 경우), 예외를 던짐.
                .orElseThrow(EntityNotFoundException::new);
        // 검색된 주문에 속한 주문 항목의 수
        assertEquals(3, savedOrder.getOrderItems().size());
        //assertEquals 메서드를 사용 시, 실제 값과 기대값을 비교. 값이 다르면 테스트가 실패.
    }


    public Order createOrder () {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(50000);
            orderItem.setCount(10);
            orderItem.setOrder(order);
            orderItem.setRegTime(LocalDateTime.now());
            orderItem.setUpdateTime(LocalDateTime.now());
            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }


    @Test
    @DisplayName("고아 객체 삭제 테스트")
    public void orphanRemovalTest(){
        Order order = createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("orderItem.getOrder().getClass() = "
                + orderItem.getOrder().getClass());
        System.out.println(" =========================================================================================== " );
        orderItem.getOrder().getOrderDate();
        System.out.println(" =========================================================================================== " );

    }

}
