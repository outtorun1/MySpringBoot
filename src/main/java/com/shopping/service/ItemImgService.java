package com.shopping.service;

import com.shopping.entity.ItemImg;
import com.shopping.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation ; //= "file:///C:/shop/"; // 상품 이미지가 업로드 되는 경로

    private final ItemImgRepository itemImgRepository ;
    private final FileService fileService ;

    private String savedImagePath = "/images/item/";
//    private String savedImagePath = "file:///C:/shop/item/";

    // 상품에 대한 이미지 정보를 저장해 줍니다.
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename() ; // 업로드 했던 이미지의 원본 파일 이름
        String imgName = ""; // uuid 형식의 저장된 이미지 이름
        String imgUrl = "" ; // 상품 이미지 불러 오는 경로

        if(!StringUtils.isEmpty(oriImgName)){ // 원본 파일 이름이 있으면 업로드 하기
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = savedImagePath + imgName ;
        }

        // 상품 이미지 정보를 저장합니다.
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);

        itemImgRepository.save(itemImg) ;
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){ // 업로드할 파일이 있으면
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                                        .orElseThrow(EntityNotFoundException::new) ;

            // 기존에 등록했던 옛날 이미지는 삭제 합니다.
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
               fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename() ;

            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = savedImagePath +  imgName;

            // 상품 이미지 파일을 업로드합니다.
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }
}
