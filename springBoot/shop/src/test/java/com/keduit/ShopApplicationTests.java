package com.keduit;

import com.keduit.constant.ItemSellStatus;
import com.keduit.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.keduit.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class ShopApplicationTests {

	@Autowired
	ItemRepository itemRepository;

	@PersistenceContext
	EntityManager em;


	@Test
	@DisplayName("상품 저장 테스트")
	public void createItem() {
		Item item = new Item();

		item.setItemNm("테스트 상품");
		item.setPrice(50000);
		item.setItemDetail("상세정보임");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(10000);
		item.setRegTime(LocalDateTime.now());
		item.setUpdateTime(LocalDateTime.now());

		itemRepository.save(item);

	}

	@Test
	public void findByItemNmTest() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
		for(Item item: itemList) {
			System.out.println("item = " + item);
		}
	}

	private void createItemList() {
		for(int i = 1; i < 51; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + 1);
			item.setPrice(50000);
			item.setItemDetail("상세정보임" +  + i);
			if(i < 11) {
				item.setItemSellStatus(ItemSellStatus.SELL);
				item.setStockNumber(10000);
			} else {
				item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
				item.setStockNumber(0);
			}
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());

			Item savedItem = itemRepository.save(item);
		}
	}
	
	@Test
	public void findByItemNmItemDetailTest() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트", "상세정보임1");
		for(Item item: itemList) {
			System.out.println("item = " + item);
		}
	}
	
	@Test
	@DisplayName("가격 LessThan 테스트")
	public void findByPriceLessThanTest() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByPriceLessThan(50001);
		for(Item item: itemList) {
			System.out.println("item = " + item);
		}
	}

	@Test
	public void findByPriceLessThanByPriceDescTest() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(50001);
		for(Item item: itemList) {
			System.out.println("item = " + item);
		}
	}
	
	@Test
	@DisplayName("Query를 이용한 상품조회 테스트")
	public void findItemDetailTest() {
		//this.createItemList();

		Pageable pageable = PageRequest.of(0, 10, Sort.by("price").descending());
		List<Item> itemList = itemRepository.findByItemDetail("상세", pageable);
		for(Item item: itemList) {
			System.out.println("item = " + item);
		}
	}
	
	@Test
	@DisplayName("nativeQuery 속성을 이용한 상품조회 테스트")
	public void findByItemDetailByNative() {
		//this.createItemList();

		Pageable pageable = PageRequest.of(0, 10, Sort.by("price").descending());
		List<Item> itemList = itemRepository.findByItemByNative("상세", pageable);
		for(Item item: itemList) {
			System.out.println("item = " + item);
		}
	}

	@Test
	public void testSelect() {
		Long id = 51L;

		this.createItemList();
		Optional<Item> result = itemRepository.findById(id); // nullcheck를 하기 위한 Optional 처리
		System.out.println("==============================");
		if(result.isPresent()) {
			Item item = result.get();
			System.out.println("item = " + item);
		}
	}

	// LazyInitializationException 지연처리 오류 발생해서 해당에러 회피를 위해 @Transactional 사용
	@Transactional
	@Test
	public void testSelect2() {
		this.createItemList();
		Long id = 51L;

		Item item = itemRepository.getOne(id);
		System.out.println("==========================");
		System.out.println("item = " + item);
	}
	
	@Test
	public void testUpdate() {
		Item item = Item.builder().id(170L).itemNm("수정된 상품명")
				.itemDetail("수정된 상세").price(56000).build();
		System.out.println("itemRepository.save(item) = " + itemRepository.save(item));
		// 캐시에 바뀐 내용을 들고 있다가, save 시점에 db에 저장 된다
	}

	@Test
	public void testDelete() {
		Long id = 51L;
		itemRepository.deleteById(id);
	}

	@Test
	public void testPageDefault() {
		//this.createItemList();

		Pageable pageable = PageRequest.of(0, 10);
		Page<Item> result = itemRepository.findAll(pageable);
		System.out.println("result = " + result);
		System.out.println("-------------------------");
		for(Item item : result.getContent()) {
			System.out.println("item = " + item);
		}
	}

	@Test
	public void testSort() {
		Sort sort1 = Sort.by("price").descending();
		Pageable pageable = PageRequest.of(0, 10, sort1);
		Page<Item> result = itemRepository.findAll(pageable);
		result.get().forEach(item-> {
			System.out.println("item = " + item);
		});
	}

	void contextLoads() {
	}
}
