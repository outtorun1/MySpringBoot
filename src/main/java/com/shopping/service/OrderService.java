package com.shopping.service;

import com.shopping.dto.OrderDto;
import com.shopping.dto.OrderHistDto;
import com.shopping.dto.OrderItemDto;
import com.shopping.entity.*;
import com.shopping.repository.ItemImgRepository;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository ;
    private final MemberRepository memberRepository ;
    private final OrderRepository orderRepository ;

    public Long order(OrderDto orderDto, String email){
        // 상품 id를 이용하여 주문할 상품 정보를 조회합니다.
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new) ;

        // 로그인 된 email 정보를 사용하여 회원 Entity를 조회합니다.
        Member member = memberRepository.findByEmail(email) ;

        // 주문할 상품들을 저장할 리스트 객체입니다.
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();

        // 상품 Entity와 주문 수량을 이용하여 주문 상품에 대한 Entity를 생성합니다.
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount()) ;

        // 주문 상품을 주문 상품 목록에 채워 넣습니다.
        orderItemList.add(orderItem);

        // 회원의 정보와 주문 상품 목록을 이용하여 주문 Entity를 생성합니다.
        Order order = Order.createOrder(member, orderItemList) ;

        orderRepository.save(order) ; // 주문 Entity를 저장합니다.

        return order.getId() ;
    }

    private final ItemImgRepository itemImgRepository ;

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        // 사용자의 아이디와 페이징 정보를 이용하여 주문 목록을 조회합니다.
        List<Order> orders = orderRepository.findOrders(email, pageable) ;

        // 회원의 주문 건수를 구합니다.
        Long totalCount = orderRepository.countOrder(email) ;

        // 고객의 주문 내역 정보들을 저장하고 있는 리스트 컬렉션입니다.
        List<OrderHistDto> orderHistDtos = new ArrayList<OrderHistDto>();

        for(Order order : orders){
            // orderHistDto는 구매 내역 페이지에서 보여줄 dto 입니다.
            OrderHistDto orderHistDto = new OrderHistDto(order) ;

            // 해당 주문시 담아둔 여러 개의 상품들 정보입니다.
            List<OrderItem> orderItems = order.getOrderItems() ;
            for(OrderItem orderItem : orderItems){
                // 주문한 상품들의 대표 이미지를 조회합니다.
                Long itemId = orderItem.getItem().getId() ;
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(itemId, "Y") ;
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto) ;
        }
        // 페이징 구현 객체를 반환합니다.
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }
}













