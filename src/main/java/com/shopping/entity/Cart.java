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
}

