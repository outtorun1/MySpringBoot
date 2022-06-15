package com.shopping.dto;

import com.shopping.constant.ItemSellStatus;
import com.shopping.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {
    private Long id ;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm ;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price ;

    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    private String itemDetail ;

    @NotNull(message = "재고 수량은 필수 입력 값입니다.")
    private Integer stockNumber ;

    private ItemSellStatus itemSellStatus ;

    // 상품 등록시 첨부할 상품 이미지 정보들을 저장할 리스트 컬렉션입니다.(최대 5개 이미지)
    private List<ItemImgDto> itemImgDtoList = new ArrayList<ItemImgDto>() ;

    // 상품 수정시 해당 이미지들의 unique id 값을 저장할 리스트 컬렉션입니다.
    private List<Long> itemImgIds = new ArrayList<Long>() ;

    private static ModelMapper modelMapper = new ModelMapper() ;

    public Item createItem(){
        return modelMapper.map(this, Item.class) ;
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }

}







