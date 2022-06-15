package com.shopping.entity;

import com.shopping.constant.ItemSellStatus;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderItemRepository;
import com.shopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

public class EntityMapping {
    @Autowired
    ItemRepository itemRepository ;

    @Autowired
    OrderRepository orderRepository ;

    @PersistenceContext
    EntityManager em ; // JPA에서 Entity를 관리해주는 관리자 역할

    @Autowired
    MemberRepository memberRepository ;

    @Autowired
    OrderItemRepository orderItemRepository ;

    public Order createOrder() {
        Order order = new Order() ;
        for (int i = 0; i < 3; i++) {
            Item item = this.createItem() ;
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem() ;
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem) ;
        }

        Member member = new Member();
        memberRepository.save(member) ;
        order.setMember(member);
        orderRepository.save(order) ;
        return order ;
    }

    public Item createItem() {
        Item item = new Item() ;

        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상품 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        return item ;
    }
}
