package com.shopping.repository;

import com.shopping.dto.CartDetailDto;
import com.shopping.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 카트 정보와 상품 정보를 이용하여 특정 장바구니에 상품을 조회합니다.
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    // 특정 카트 안에 들어있는 카트 상품 목록들을 조회합하되, 상품 이미지는 대표 이미지만 조회합니다.
    @Query(" select new com.shopping.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            " from CartItem ci, ItemImg im " +
            " join ci.item i " +
            " where ci.cart.id = :cartId " +
            " and im.item.id = ci.item.id " +
            " and im.repImgYn = 'Y' " +
            " order by ci.regTime desc ")
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
