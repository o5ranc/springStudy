package com.keduit.entity;

import com.keduit.constant.ItemSellStatus;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@ToString
// 빌더는 필드의 초기화 작업을 도와 주는 역할
// 생성한 생성자가 없다면 @AllArgsConstructor(access = AccessLevel.PACKAGE) 가 암묵적으로 적용된다고 한다.
@Builder
@NoArgsConstructor
@AllArgsConstructor // 전체 멤버변수를 갖는 생성자를 만듬
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 코드

    private String itemNm; // 상품명

    @Column(length = 50, nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob // 큰 용량의 데이터를 넣을 때 사용
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록시간

    private LocalDateTime updateTime; // 수정시간
}
