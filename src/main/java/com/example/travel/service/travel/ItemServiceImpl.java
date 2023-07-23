package com.example.travel.service.travel;


import com.example.travel.domain.Category;
import com.example.travel.domain.Item;
import com.example.travel.dto.travel.CategoryDTO;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.repository.travel.CategoryRepository;
import com.example.travel.repository.travel.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    final ItemRepository itemRepository;
    final CategoryRepository categoryRepository;
    final CategoryService categoryService;



    @Override
    public ItemDTO itemSave(ItemDTO itemDTO) {
       log.info("아이템 저장 로직------------------");
        Item item = itemDtoToEntity(itemDTO);

        //총 여행 금액 저장
        Long categoryId = itemDTO.getCategoryId();
        int itemAccount = itemDTO.getItemAccount();
        log.info("itemAccount : {}",itemAccount);
        Category category = categoryRepository.getOne(categoryId);
        CategoryDTO categoryDTO = categoryService.categoryEntityToDto(category);
        log.info("itemAccount category : {}",category );

        if (itemAccount >= 0){
            log.info("금액이 있으면 저장!");
            int categoryTotalPrice = categoryDTO.getCategoryTotalPrice();
            categoryTotalPrice += itemAccount;
            categoryDTO.setCategoryTotalPrice(categoryTotalPrice);

            Category categorySave = categoryService.categoryDtoToEntity(categoryDTO);
            categoryRepository.save(categorySave);
        }

        // 아이템 저장
        Item save = itemRepository.save(item);
        ItemDTO result = itemEntityToDto(save);



        return result;
    }

    @Override
    public List<ItemDTO> itemList(int itemDay, Long categoryNo) {
        List<Item> itemList = itemRepository.findItemList(itemDay, categoryNo);
        List<ItemDTO> itemDTOList = itemList.stream().map(i -> itemEntityToDto(i)).collect(Collectors.toList());
        return itemDTOList;
    }

    @Override
    public ItemDTO itemGet(Long itemNo) {
        Optional<Item> itemList = itemRepository.findById(itemNo);
        if (itemList.isPresent()){
            Item item = itemList.get();
            ItemDTO itemDTO = itemEntityToDto(item);
            return itemDTO;
        }else {
            return null;
        }
    }

    @Override
    public boolean itemDelete(Long itemNo) {
        itemRepository.deleteById(itemNo);
        return true;
    }

    @Override
    public void itemListOrderBySet(List<ItemDTO> itemList) {
        for(int i= 0; i<itemList.size();i++){
            Item item = itemRepository.getOne(itemList.get(i).getItemNo());
            log.info("item : {}",item);
            ItemDTO itemDTO = itemEntityToDto(item);
            itemDTO.setItemNumber(itemList.get(i).getItemNumber());
            log.info("itemDTO : {}",itemDTO);
            Item result = itemDtoToEntity(itemDTO);
            Item save = itemRepository.save(result);
        }
    }
}
