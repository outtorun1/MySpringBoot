package com.shopping.repository;

import com.shopping.constant.ItemSellStatus;
import com.shopping.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest // 테스트를 위하여 스프링 부트에서 제공해주는 어노테이션
public class ItemRepositoryTest {
    @Autowired // 객체를 외부에서 자동으로 연동 시켜줍니다.
    ItemRepository itemRepository ;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item() ;
        item.setItemNm("사과");
        item.setItemDetail("사과는 맛있어");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setPrice(10000);
        item.setRegTime(LocalDateTime.now());
        item.setStockNumber(100);
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item) ;
        System.out.println(savedItem.toString());
    }
}
