package com.shopping.controller;

import com.shopping.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/thymeleaf")
public class ThymeleafExController {
    @GetMapping(value = "/ex01")
    public String thymeleafExam01(Model model){
        model.addAttribute("data", "타임 리프 1번 예시입니다");
        return "thymeleafEx/viewEx01" ;
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExam02(Model model){
        ItemDto itemDto = new ItemDto() ;
        itemDto.setItemDetail("맛있어요");
        itemDto.setItemNm("사과");
        itemDto.setPrice(1234);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/viewEx02" ;
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExam03(Model model){
        List<ItemDto> itemDtoList = new ArrayList<ItemDto>();

        for (int i = 1; i <= 10 ; i++) {
            ItemDto itemDto = new ItemDto() ;
            itemDto.setItemDetail("맛있어요");
            itemDto.setItemNm("사과");
            itemDto.setPrice(1234);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/viewEx03" ;
    }

    @GetMapping(value = "/ex04")
    public String thymeleafExam04(Model model){
        List<ItemDto> itemDtoList = new ArrayList<ItemDto>();

        for (int i = 1; i <= 10 ; i++) {
            ItemDto itemDto = new ItemDto() ;
            itemDto.setItemDetail("맛있어요");
            itemDto.setItemNm("사과");
            itemDto.setPrice(1234);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/viewEx04" ;
    }

    @GetMapping(value = "/ex05")
    public String thymeleafExam05(Model model){
        List<ItemDto> itemDtoList = new ArrayList<ItemDto>();

        for (int i = 1; i <= 10 ; i++) {
            ItemDto itemDto = new ItemDto() ;
            itemDto.setItemDetail("맛있어요");
            itemDto.setItemNm("사과");
            itemDto.setPrice(1234);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/viewEx05" ;
    }

    @GetMapping(value = "/ex06")
    public String thymeleafExam06(){
        return "thymeleafEx/viewEx06" ;
    }

    @GetMapping(value = "/ex07") // viewEx06.html에서 클릭을 하시면 됩니다.
    public String thymeleafExam07(String param1, String param2, Model model){
        if(param1 == null){param1="호호호";}
        if(param2 == null){param2="크크크";}

        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleafEx/viewEx07" ;
    }

    @GetMapping(value = "/ex08")
    public String thymeleafExam08(){
        return "thymeleafEx/viewEx08" ;
    }
}
