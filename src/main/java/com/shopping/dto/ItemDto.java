package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// DTO(Data Transfer Object) : 데이터 전송 객체
// 일반적으로 화면(form)에서 넘어 오는 파라미터들을 저장해주는 자바 클래스를
// 데이터 전송 객체라고 합니다. 스프링에서는 이것을 command 객체라고 부릅니다.
@Getter @Setter
public class ItemDto {
    private Long id ;
    private String itemNm ;
    private Integer price ;
    private String itemDetail ;
    private String sellStatCd ;
    private LocalDateTime regTime ;
    private LocalDateTime updateTime ;
}
