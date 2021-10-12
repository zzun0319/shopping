package com.shopping.controller;

import com.shopping.api.dtos.ItemDto;
import com.shopping.domain.items.Item;
import com.shopping.repository.ItemRepository;
import com.shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping
    public String items(Model model, @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Item> page = itemRepository.findAll(pageable);
        Page<ItemDto> dtoPage = page.map(ItemDto::new);
        model.addAttribute("page", dtoPage);
        return "item/item-list";
    }
}
