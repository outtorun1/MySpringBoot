package com.shopping.repository;

import com.shopping.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 상품에 대한 이미지 정보를 위한 Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    // 특정 상품(Item)과 연관된 상품 이미지(ItemImg)를 아이디를 이용하여 오름차순으로 정렬하여 조회
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId) ;

    // 상품 아이디를 이용하여 특정 상품에 대한 대표 이미지를 조회합니다.
    // 대표 이미지는 repimgYn 매개 변수에 "Y"가 입력되어야 합니다.
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repimgYn);
}