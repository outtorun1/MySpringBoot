package com.shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
public class OrderTestC extends EntityMapping{
    @Test
    @DisplayName("지연 로딩 테스트")
    public void LazyLoadingTest(){
        Order order = this.createOrder() ;

        // 주문한 상품들 중에서 1번째 상품의 아이디
        Long orderItemId = order.getOrderItems().get(0).getId() ;

        em.flush(); // 영속성 컨텍스트의 내용을 데이터 베이스에 반영하기
        em.clear(); // 영속성 컨텍스트 상태 초기화

        // 주문 상품 번호 orderItemId에 대하여 조회를 해보도록 하겠습니다.
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                                .orElseThrow(EntityNotFoundException::new) ;

        System.out.println("Order Class : " + orderItem.getOrder().getClass());
        System.out.println("==================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("==================================");
    }
}