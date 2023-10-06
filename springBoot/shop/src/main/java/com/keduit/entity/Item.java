package com.keduit.entity;

import com.keduit.constant.ItemSellStatus;
import com.keduit.dto.ItemFormDTO;
import com.keduit.exception.OutOfStockException;
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
public class Item extends BaseEntity {

    @Id
 	@Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 코드

    //nullable = false, null X
    @Column(nullable = false, length =50)
    private String itemNm; //상품명

    @Column(name="price", length = 50, nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockNumber; // 재고수량

	//여기서 @Lob은 "대량의 텍스트"를 관리하는 것 BLOB,CLOB 타입 매핑 BLOB(Binary Large Object) CLOB(Character Large Object)
    @Lob // 큰 용량의 데이터를 넣을 때 사용
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

	// 열거형(Enum)숫자나 코드 값으로 저장하는 대신 문자열 형태로 저장하고 싶을 때  사용
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

// BaseEntity 에 선언하고 extends 해서 이부분 뺌
//    private LocalDateTime regTime; //등록시간
//
//    private LocalDateTime updateTime; //수정시간

    public void updateItem(ItemFormDTO itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.itemDetail = itemFormDto.getItemDetail();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemSellStatus = itemFormDto.getItemSellStatus();

    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if(restStock <= 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock; // 남은 수량을 현재 수량으로 재할당
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber; // 상품 취소 수량 만큼 증가
    }
}
