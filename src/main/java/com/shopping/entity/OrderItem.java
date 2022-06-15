package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY) // 상품들은 여러 개의 주문상품에 포함될 수 있습니다.
    @JoinColumn(name = "item_id")
    private Item item ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order ;

    private int orderPrice ; // 단가
    private int count ; // 수량
//    private LocalDateTime regTime ;
//    private LocalDateTime updateTime ;

    // 주문할 상품 정보와 주문 수량을 이용하여 OrderItem 객체를 생성합니다.
    public static OrderItem createOrderItem(Item item, int count){
        // orderItem : 특정 상품에 대하여 주문 수량과 가격 정보를 담고 있는 객체
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count); // 재고 수량 감소
        return orderItem ;
    }

    public int getTotalPrice(){
        return orderPrice * count ; // 금액 = 가격 * 수량
    }
}
