package com.shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderTestB extends EntityMapping{
    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0) ; // 0번째 요소 제거하기
        em.flush(); // 영속성 컨텍스트에 있는 내용을 데이터베이스에 반영시킵니다.
    }
}