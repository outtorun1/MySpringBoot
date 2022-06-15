package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="item_img")
@Getter @Setter // JPA와 연계되는 Entity
public class ItemImg extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String imgName ; // UUID 형식으로 업로드된 이미지 파일 이름
    private String oriImgName ; // 이미지 원본 이름
    private String imgUrl ;
    private String repImgYn ; // 대표 이미지는 값이 Y

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item ;

    // 이미지 정보를 업데이트 해주는 메소드
    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName ;
        this.imgName = imgName ;
        this.imgUrl = imgUrl ;
    }
}
