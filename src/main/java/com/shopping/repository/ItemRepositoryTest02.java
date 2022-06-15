package com.shopping.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.constant.ItemSellStatus;
import com.shopping.entity.Item;
import com.shopping.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ItemRepositoryTest02 {
    @Autowired // null인 객체를 외부에서 주입(injection)해주는 어노테이션
    ItemRepository itemRepository ;

    // 샘플용 데이터 10개만 생성해 줍니다.
    private void createItemTestMany() {
        String[] fruit = {"사과", "배", "오렌지"};
        String[] description = {"달아요", "맛있어요", "맛없어요", "떫어요"};
        int[] stock = {100, 200, 300, 400};
        int[] price = {111, 222, 333, 444, 555};

        for (int i = 1; i <=10 ; i++) {
            Item item = new Item();

            item.setItemNm(fruit[i % fruit.length]);
            item.setPrice(price[i % price.length]);
            item.setItemDetail(description[i % description.length]);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(stock[i % stock.length]);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString()) ;
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        //this.createItemTestMany() ;
        List<Item> itemList = itemRepository.findByItemNm("오렌지");
        for(Item item : itemList){
            System.out.println(item.toString()) ;
        }
    }

    @Test
    @DisplayName("가격 less than 테스트하기")
    public void findByPriceLessThan(){
        List<Item> itemList = itemRepository.findByPriceLessThan(300);
        for(Item item : itemList){
            System.out.println(item.toString()) ;
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(300);
        for(Item item : itemList){
            System.out.println(item.toString()) ;
        }
    }

    @Test
    @DisplayName("@Query를 사용한 상품 조회 테스트")
    public void findByItemDetail(){
        List<Item> itemList = itemRepository.findByItemDetail("어");
        for(Item item : itemList){
            System.out.println(item.toString()) ;
        }
    }

    @Test
    @DisplayName("@Query를 사용한 상품 조회 테스트 2")
    public void findByItemDetail2(){
        List<Item> itemList = itemRepository.findByItemDetail2("어");
        for(Item item : itemList){
            System.out.println(item.toString()) ;
        }
    }

    @PersistenceContext // JPA가 동작하는 영속성 작업 구간
    EntityManager em ; // 엔터티 관리자

    @Test
    @DisplayName("query Dsl Test01")
    public void queryDslTest01(){
        // JPAQueryFactory : 쿼리를 만들어 주는 클래스
        JPAQueryFactory queryFactory = new JPAQueryFactory(em) ;

        // Item Entity에서 판매 중인 상품 들을 가격 역순으로 출력해봅니다.
        // like 연산자로 데이터 필터 검색할 겁니다.("달아요" 찾기)
        QItem qitem = QItem.item ;
        JPAQuery<Item> query = queryFactory
                .selectFrom(qitem)
                .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qitem.itemDetail.like("%" + "아" + "%"))
                .orderBy(qitem.price.desc()) ;

        List<Item> itemList = query.fetch() ;
        for(Item item : itemList){
            System.out.println(item.toString()) ;
        }
    }

    @Test
    @DisplayName("데이터 생성(for QuerydslPredicateExecutor)")
    public void createItemListNew(){
        String[] fruit = {"사과", "배", "오렌지"} ;
        String[] description = {"달아요", "맛있어요", "맛없어요", "떫어요"} ;
        int[] stock = {100, 200, 300, 400, 500, 600};
        int[] price = {111, 222, 333, 444, 555};

        for (int i = 1; i <= 30 ; i++) {
            Item item = new Item();

            item.setItemNm(fruit[i%fruit.length]);
            item.setPrice(price[i%price.length]);
            item.setItemDetail(description[i%description.length]);
            if(i%2==0){
                item.setItemSellStatus(ItemSellStatus.SELL);
            }else{
                item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            }
            item.setStockNumber(stock[i%stock.length]);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 queryDsl 조회 테스트02")
    public void queryDslTest2(){
        // 판매 중인 상품 중에서 단가가 200초과이고, 상품 설명에 '어'가 포함되어 있는 상품을 조회하세요.
        // 1페이지에 2개씩 볼건데, 2페이지를 좀 보여 주세요.
//        select * from item
//        where item_sell_status = 'SELL'
//        and price > 200
//        and item_detail like '%어%' ;

        String itemDetail = "어" ;
        int price = 200 ;
        String itemSellStat = "SELL" ;

        QItem item = QItem.item ;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%")) ;
        booleanBuilder.and(item.price.gt(price)) ;
        booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));

        Pageable pageable = PageRequest.of(1, 2) ;

        Page<Item> itemPagingResult
                = itemRepository.findAll(booleanBuilder, pageable) ;
        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent() ;
        for(Item resultItem : resultItemList){
            System.out.println(resultItem.toString());
        }
    }
}