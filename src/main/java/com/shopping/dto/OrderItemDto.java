package com.shopping.dto;

import com.shopping.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

// 주문 상품 1개의 정보를 담고 있는 클래스
@Getter @Setter
public class OrderItemDto {
    private String itemNm ; // 상품명
    private int count ; // 주문 수량
    private int orderPrice ; // 주문 금액
    private String imgUrl ; // 상품 이미지 경로

    // 주문 상품 정보와 해당 이미지 경로를 입력 받아서 멤버 변수들에 값을 대입합니다.
    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm() ;
        this.count = orderItem.getCount() ;
        this.orderPrice = orderItem.getOrderPrice() ;
        this.imgUrl = imgUrl ;
    }
}
