package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CartDetailDto {
    private Long cartItemId;    // 장바구니 상품을 위한 아이디
    private String itemNm;      // 상품 이름
    private int price;          // 상품 금앰
    private int count;          // 구매 하고자 하는 수량
    private String imgUrl;      // 상품 이미지 경로

    // 장바구니 조회 페이지에 전달해줄 데이터들을 생성자(constructor)를 이용하여 주입(injection)해줍니다.

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
