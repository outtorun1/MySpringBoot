package com.shopping.dto;

import com.shopping.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter // 화면과 연동되는 dto 객체
public class ItemImgDto {
    private Long id ;
    private String imgName ;
    private String oriImgName ;
    private String imgUrl ;
    private String repImgYn ;

    // mapper 객체 생성
    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        // 상품의 이미지 정보를 이용하여 상품 dto 객체로 변환해 줍니다.
        return modelMapper.map(itemImg, ItemImgDto.class) ;
    }
}







