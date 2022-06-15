package com.shopping.controller;

import com.shopping.dto.ItemFormDto;
import com.shopping.dto.ItemSearchDto;
import com.shopping.entity.Item;
import com.shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        // itemFormDto : 상품 등록을 하게 되면 이 객체에 데이터 정보가 들어 갑니다.
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm" ;
    }

    // 스프링에서 itemFormDto 객체를 command 객체라고 부릅니다.
    // 입력한 파라미터들이 여기에 모두 담겨져 컨트롤러 메소드로 들어옵니다.

    // 파라미터 itemImgFile이 넘겨준 데이터들을 변수 itemImgFileList에 저장해 주세요.
    // List<MultipartFile>라고 한 이유는 <input type="file">이 여러 개(5개)이기 때문입니다.

    private final ItemService itemService ;

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList ){
        // itemFormDto) 상품 등록을 위하여 사용자가 기입한 값
        // bindingResult) 유효성 검사에 문제가 있으면, 여기에 기록이 됩니다.
        // model) 저장할 데이터 또는 별도의 메시지 등을 html로 보내기 위한 Model 객체
        // itemImgFileList) 업로드를 위한 상품 이미지들을 저장하고 있는 리스트 컬렉션

        if(bindingResult.hasErrors()){
            return "item/itemForm" ;
        }

        // 첫 번째 상품은 대표 이미지이기 때문에 필수 사항입니다.
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수 입력 값입니다.") ;
            return "item/itemForm" ;
        }

        try{
            itemService.saveItem(itemFormDto, itemImgFileList) ;
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중에 오류가 발생하였습니다.") ;
            return "item/itemForm" ;
        }

        return "redirect:/" ; // 메인 페이지로 이동합니다.
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
        // Long itemId = Long.parseLong(request.getParameter("itemId")) ;

        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId) ;
            model.addAttribute("itemFormDto", itemFormDto) ;

        }catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재 하지 않는 상품입니다.") ;
            model.addAttribute("itemFormDto", new ItemFormDto()) ;
        }

        return "item/itemForm" ;
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        // itemFormDto) 화면에서 넘어 오는 command 객체
        // bindingResult) 폼 유효성 검사시 문제가 있는 지 체크하기 위한 클래스
        // itemImgFileList) 폼에서 넘어 오는 여러 개의 <input type="file"> 객체 리스트
        // model) 뷰 영역으로 넘겨줄 정보들을 바인딩하기 위한 모델 객체

        String whenError = "item/itemForm";

        if (bindingResult.hasErrors()){
            return whenError ;
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null ){
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력 값입니다.");
            return whenError ;
        }

        try{
            itemService.updateItem(itemFormDto, itemImgFileList) ;
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중에 오류가 발생하였습니다.");
            e.printStackTrace();
            return whenError ;
        }

        return "redirect:/"; // 메인 페이지로 이동
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page,
                             Model model){

        // Integer page = Integer.parseInt(request.getParameter("page")) ;

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 2) ;

        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable) ;

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto); // for 검색 조건 보존
        model.addAttribute("maxPage", 4); // 하단에 보여줄 페이지 번호의 최대 갯수

        return "item/itemMng" ;
    }

    // 메인 화면에서 상품 이미지를 클릭하면 상품 상세 페이지로 이동합니다.
    @GetMapping("/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId) ;
        model.addAttribute("item", itemFormDto) ;
        return "item/itemDtl" ;
    }
}









