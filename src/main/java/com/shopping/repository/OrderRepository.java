package com.shopping.repository;

import com.shopping.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 로그인한 회원의 주문 정보를 페이징 조건에 맞추어서 보여 주되, 가장 최근 날짜를 먼저 조회합니다.
    @Query("select o from Order o " +
            " where o.member.email = :email " +
            " order by o.orderDate desc ")
    List<Order> findOrders(@Param("email") String email, Pageable pageable) ;

    // 회원의 주문 건수를 구해주는 메소드입니다.
    @Query("select count(o) from Order o " +
            " where o.member.email = :email")
    Long countOrder(@Param("email") String email) ;
}
