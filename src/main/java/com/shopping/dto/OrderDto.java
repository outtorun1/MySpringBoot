package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 상품 상세 페이지에서 넘어온 상품 id와 주문 수량을 전달하기 위한 dto 클래스
@Getter @Setter
public class OrderDto {
    // 상품 id
    @NotNull(message = "상품 아이디는 필수 입력 사항입니다.")
    private Long itemId ;

    // 주문 수량
    private final int MAX_VALUE = 999 ;

    @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
    @Max(value = MAX_VALUE, message = "최대 주문 수량은 " + MAX_VALUE + "개입니다.")
    private int count ;
}
