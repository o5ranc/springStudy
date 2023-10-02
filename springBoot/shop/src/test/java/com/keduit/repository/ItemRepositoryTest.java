package com.keduit.repository;

import com.keduit.constant.ItemSellStatus;
import com.keduit.entity.Item;
import com.keduit.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@SpringBootTest
                            //데이터 사용하는 곳이 다르다는 것을 표기
//@TestPropertySource(locations= "classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    //필드 주입
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){

        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("test");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);

        System.out.println("savedItem : "+savedItem.toString());
    }


    @Test
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for(Item item : itemList){
            System.out.println("item = " + item);
        }
    }

    private void createItemList(){
        for(int i=1; i<=20; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("test"+i);
            if(i< 11){
                item.setItemSellStatus(ItemSellStatus.SELL);
                item.setStockNumber(100);
            }else{
                item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
                item.setStockNumber(0);
            }
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("queryDslTest")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        List<Item> list = queryFactory
                .select(qItem)
                .from(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "test" + "%"))
                .orderBy(qItem.price.desc())
                .fetch();
        for (Item item : list){
            System.out.println("item = " + item);
        }
    }

    @Test
    @DisplayName("queryDslTest2")
    public void queryDslTest2() {
        this.createItemList();
        //and나 or조건을 묶을때 BooleanBuilder 사용
        BooleanBuilder builder = new BooleanBuilder();
        QItem item =QItem.item;
        String itemDetail = "test";
        int price = 10001;
        String itemSellStat = "SELL";

        builder.and(item.itemDetail.like("%"+itemDetail+"%"));
        //도메인에서 만든 item.price
        builder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        };
        Pageable pageable = PageRequest.of(0,5);
        Page<Item> result = itemRepository.findAll(builder, pageable);

        System.out.println("전체 페이지 수 = " + result.getTotalPages());
        System.out.println("전체 상품 수 = " + result.getTotalElements());
        System.out.println("현재 페이지 수 = " + result.getPageable().getPageNumber());
        System.out.println("CONTENT = " + result.getContent());
    }


    @Test
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList =
                itemRepository.findByItemNmOrItemDetail("테스트 상품1", "test5");
        for(Item item : itemList){
           System.out.println(item.toString());

        }
    }


    @Test
    public  void  findByPriceLessThanTest(){

        this.createItemList();
        //findByPriceLessThan()에 들어가는 숫자보다 작은 것들을 뽑음.
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList){
            System.out.println("findByPriceLessThanTest  : "+item.toString());
        }

    }

    @Test
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println("item = " + item);
        }
    }
    
    @Test
    @DisplayName("@query를 이용한 상품조회 테스트")
    public void findItemDetailTest(){
        this.createItemList();
        Pageable pageable = PageRequest.of(1,10, Sort.by("price").descending());
        List<Item> itemList = itemRepository.findByItemDetail("test",pageable);
        for(Item item : itemList){
            System.out.println("item = " + item);
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest(){
        this.createItemList();
        Pageable pageable = PageRequest.of(1,10, Sort.by("price").descending());
        List<Item> itemList = itemRepository.findByItemByNative("test", pageable);
        for(Item item : itemList){
            System.out.println("item = " + item);
        }
    }

    @Test
    public void testSelect(){
        //
        this.createItemList();
        Long id = 10L;
        //Optional 로 null처리
        Optional<Item> result = itemRepository.findById(id);
        System.out.println(" =========================== " );
        if (result.isPresent()){
            Item item = result.get();
            System.out.println("item = " + item);
        }
    }


    @Transactional    //지연 처리, 후 처리.
    @Test
    public void testSelect2(){
        Long id = 1L;
        Item item = itemRepository.getOne(id);
        //getOne 보다는 getById를 활용.
        System.out.println("============================");
        System.out.println("item = " + item);
    }

    @Test
    public void testUpdate(){
        Item item = Item.builder().id(10L).itemNm("수정된 상품명")
                .itemDetail("수정된 상세").price(2500).build();
        System.out.println("itemRepository.save(item) = "+itemRepository.save(item));
    }

    @Test
    public void testDelete(){
        Long id = 11L;
        itemRepository.deleteById(id);
    }

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> result = itemRepository.findAll(pageable);
        System.out.println("result = " + result);
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        for(Item item : result.getContent()){
            System.out.println("item = " + item);
        }
    }
    @Test
    public void testSort(){
        Sort sort1 = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Item> result = itemRepository.findAll(pageable);
        for(Item item: result.getContent()){
            System.out.println("item = " + item);
        }
    }
}


