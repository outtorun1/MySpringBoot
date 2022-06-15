package com.shopping.repository;

import com.shopping.dto.ItemSearchDto;
import com.shopping.dto.MainItemDto;
import com.shopping.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    // 상품 조회 조건 itemSearchDto와 페이징 정보 pageable를 이용하여 데이터를 조회합니다.
    // Pageable는 JPA에서 페이징 처리를 도와주는 유틸리티 성격의 인터페이스입니다.
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) ;

    // 메인 페이지에서 보여줄 상품 리스트를 구해 줍니다.
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) ;
}
