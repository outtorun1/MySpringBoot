package com.shopping.controller;

import com.shopping.dto.ItemSearchDto;
import com.shopping.dto.MainItemDto;
import com.shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService ;

    @GetMapping(value = "/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3) ;
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable) ;

        // 검색 조건에 null이 아닌 문자열 "null"이 넘어 오는 경우가 있으므로 추가 요망
        if(itemSearchDto.getSearchQuery() != null){
            if(itemSearchDto.getSearchQuery().equals("null")){
                itemSearchDto.setSearchQuery(null);
            }
        }

        model.addAttribute("items", items) ;
        model.addAttribute("itemSearchDto", itemSearchDto) ;
        model.addAttribute("maxPage", 5) ;
        return "main";
    }
}