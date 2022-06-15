package com.shopping.repository;

import com.shopping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Repository는 데이터 베이스에 대한 CRUD 작업을 수행하는 인터페이스로 구현 합니다.
public interface ItemRepository
        extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    // 상품 이름으로 데이터를 조회합니다.
    List<Item> findByItemNm(String itemNm);

    // 단가가 price보다 적은 상품들을 조회합니다.
    List<Item> findByPriceLessThan(Integer price) ;

    // 단가가 price보다 적은 상품들을 조회하되, 단가가 큰 상품부터 보여 줍니다.
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price) ;

    // like 연산자를 사용한 상품 설명(itemDetail 컬럼) 데이터 조회
    @Query(" select i from Item i where i.itemDetail like %:itemDetail% " +
        " order by i.price desc ")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail) ;

    // nativeQuery = true 옵션은 특정 데이터 베이스에 종속적인 문장을 사용하고자 할 때
    // 사용하는 옵션입니다.
    // 데이터 베이스의 컬럼 구조를 보고 주의하여 코딩 요망
    @Query(value = " select * from Item i where i.item_Detail like %:itemDetail% " +
            " order by i.price desc ", nativeQuery = true)
    List<Item> findByItemDetail2(@Param("itemDetail") String itemDetail) ;
}



