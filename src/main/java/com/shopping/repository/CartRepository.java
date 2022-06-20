package com.shopping.repository;

import com.shopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
    // 로그인한 회원의 카트 정보를 구합니다.
    // 참고로, 회원과 카트는 일대일 관계를 맺고 있습니다.
    Cart findByMemberId(Long memberId);

}
