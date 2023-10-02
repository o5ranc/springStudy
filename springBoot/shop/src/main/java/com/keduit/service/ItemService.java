package com.keduit.service;

import com.keduit.dto.ItemFormDTO;
import com.keduit.dto.ItemImgDTO;
import com.keduit.entity.Item;
import com.keduit.entity.ItemImg;
import com.keduit.repository.ItemImgRepository;
import com.keduit.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
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
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {
        // 상품 등록
        Item item = itemFormDTO.createItem(); // repository에 등록하기 위해 DTO를 객체에 넣기
        itemRepository.save(item);

        // 이미지 등록
        for(int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
            }

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

    // 읽기만 하는 조회 쿼리인데 JPA가 영속성 변화/변경 여부를 감시할 필요 없게 성능향상을 위한 추가 부분
    @Transactional(readOnly = true)
    public ItemFormDTO getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDTO> itemImgDTOList = new ArrayList<>();

        for(ItemImg itemImg: itemImgList) {
            ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
            itemImgDTOList.add(itemImgDTO);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDTO itemFormDTO = ItemFormDTO.of(item);
        itemFormDTO.setItemImgDTOList(itemImgDTOList);
        return itemFormDTO;
    }
}
