package com.shopping.entity;

import com.shopping.constant.ItemSellStatus;
import com.shopping.dto.ItemFormDto;
import com.shopping.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item") // name 속성은 실제 테이블 이름이 됩니다.
@Getter @Setter @ToString
public class Item extends BaseEntity {
    @Id
    @Column(name = "item_id")
    // AUTO는 JPA 구현체가 자동으로 기본키 생성 전략을 결정합니다.
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ; // 상품 코드(기본 키역할)

    // 테이블에서 not null 옵션과 동일합니다.
    // 최대 길이는 50인데, 필수 입력 사항입니다.
    @Column(nullable = false, length = 50)
    private String itemNm ; // 상품 이름

    @Column(nullable = false, name = "price")
    private int price ; // 가격

    @Column(nullable = false)
    private int stockNumber ; // 재고 수량

    @Lob // CLOB(Character Large OBject), BLOB(Binary Large OBject)
    @Column(nullable = false)
    private String itemDetail ; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus  ; // 상품 판매 상태

//    private LocalDateTime regTime ; // 등록 시간
//    private LocalDateTime updateTime ; // 수정 시간

    // 상품(Item)에 대한 정보를 업데이트 해주는 메소드
    public void updateItem(ItemFormDto itemFormDto){
        // itemFormDto는 화면에서 넘겨 주는 수정될 dto 객체 정보로써,
        // 모든 변수들의 내용들을 Entity 변수에 저장시켜 주도록 합니다.
        this.itemNm = itemFormDto.getItemNm() ;
        this.price = itemFormDto.getPrice() ;
        this.stockNumber = itemFormDto.getStockNumber() ;
        this.itemDetail = itemFormDto.getItemDetail() ;
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    // 상품 주문시 재고 수량을 감소해주는 메소드입니다.
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber ;

        if(restStock<0){ // 재고 부족시 예외 발생 시키기
            String message = "상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")" ;
            throw new OutOfStockException(message);
        }

        // 재고가 충분하므로 주문된 후 남은 재고량으로 갱신
        this.stockNumber = restStock ;
    }
}
