package com.shopping.dto;


import com.shopping.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

// 상품 검색 조건을 저장하고 있는 dto
@Getter @Setter
public class ItemSearchDto {
    private String searchDateType ; // 조회 기간 범위
    private ItemSellStatus searchSellStatus ; // 판매중/품절 중 2가지 모드 중에서 택일
    private String searchBy ; // 검색 필드(상품 이름 또는 등록자 아이디)
    private String searchQuery ; // 검색 키워드
}








