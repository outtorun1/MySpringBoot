package com.shopping.service;

import com.shopping.dto.ItemFormDto;
import com.shopping.dto.ItemImgDto;
import com.shopping.dto.ItemSearchDto;
import com.shopping.dto.MainItemDto;
import com.shopping.entity.Item;
import com.shopping.entity.ItemImg;
import com.shopping.repository.ItemImgRepository;
import com.shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository ;
    private final ItemImgService itemImgService ;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        // 01. 상품 등록
        // 상품 등록 화면에서 넘어온 데이터를 이용하여 Item Entity를 생성합니다.
        Item item = itemFormDto.createItem() ;
        itemRepository.save(item) ; // 상품 데이터 저장

        // 02. 상품에 들어 가는 각 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();

            // 해당 상품과 이미지와 연계를 맺어 줌
            // 실제 데이터 베이스에 item_id 컬럼에 동일한 값이 들어 갑니다.
            itemImg.setItem(item);

            if(i==0){ // 1번째 이미지는 대표 이미지로 지정하기
                itemImg.setRepImgYn("Y");
            }else{
                itemImg.setRepImgYn("N");
            }

            // 상품의 이미지 정보를 저장합니다.
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));

        } // end for

        return item.getId() ;
    }

    private final ItemImgRepository itemImgRepository ;

    // 상품 아이디를 이용하여 상품 이미지 목록을 구해옵니다.
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        // 상품 이미지와 관련된  Entity 목록을 조회합니다.
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId) ;

        // dto를 저장시킬 리스트 컬렉션
        List<ItemImgDto> itemImgDtoList = new ArrayList<ItemImgDto>();

        // 반복문을 사용하여 Entity를 Dto로 변경 시켜 dto 컬렉션에 담습니다.
        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg) ;
            itemImgDtoList.add(itemImgDto);
        }

        // 상품 Entity 정보를 구합니다.
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new) ;
        ItemFormDto itemFormDto = ItemFormDto.of(item) ;
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto ;
    }

    // 화면(dto)에서 넘겨진 상품(item) 정보를 업데이트 합니다.
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        Item item = itemRepository.findById(itemFormDto.getId())
                        .orElseThrow(EntityNotFoundException::new);

        item.updateItem(itemFormDto); // 화면에서 넘어온 dto를 이용하여 item Entity에게 전달합니다.

        // 5개의 이미지에 대한 각각의 id 목록
        List<Long> itemImgIds = itemFormDto.getItemImgIds() ;

        // 각각의 상품 이미지 정보를 수정해줍니다.
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId(); // 수정된 상품의 id를 반환합니다.
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        // 상품 검색 조건을 이용하여 페이징 객체를 반환합니다.
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable) ;
    }
}









