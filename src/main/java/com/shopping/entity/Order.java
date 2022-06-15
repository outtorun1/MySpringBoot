package com.shopping.entity;

import com.shopping.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order extends BaseEntity {
    @Id@GeneratedValue
    @Column(name="order_id")
    private Long id ; //

    @ManyToOne(fetch = FetchType.LAZY) // 한 사람이 여러 번의 주문을 할 수 있습니다.
    private Member member ; //

    private LocalDateTime orderDate ; // 주문 날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus ; // 주문 상태

//    private LocalDateTime regTime ; // 등록 시간
//    private LocalDateTime updateTime ; // 수정 시간

    // CascadeType.ALL : 부모 Entity의 영속성 상태 변화를 자식 Entity에게 모두 전이하시오.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem) ; // 주문된 상품들을 컬렉션에 저장합니다.
        orderItem.setOrder(this);
    }
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member); // 누가 이 주문을 하고 있는가.

        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order ;
    }
    public int getTotalPrice(){
        // 모든 상품들의 sum(단가*수량)을 구합니다.
        int totalPrice = 0;
        for(OrderItem orderItem:orderItems){
            totalPrice += orderItem.getTotalPrice() ;
        }
        return totalPrice;
    }
}














