package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter @Setter @ToString
public class Cart extends BaseEntity { // 장바구니
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    // 장바구니가 회원 Entity를 참조하고 있습니다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") // 이 컬럼은 나중에 포린키가 됩니다.
    private Member member ;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        // 특정 장바구니는 특정 회원의 것이빈다. (일대일 연관 관계 매핑)
        cart.setMember(member);
        return cart;
    }
}

