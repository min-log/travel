package com.example.travel.service.travel;


import com.example.travel.domain.Item;
import com.example.travel.dto.travel.ItemDTO;
import com.example.travel.repository.travel.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    final ItemRepository itemRepository;



    @Override
    public ItemDTO itemSave(ItemDTO itemDTO) {
       log.info("아이템 저장 로직------------------");
        Item item = itemDtoToEntity(itemDTO);
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
}
