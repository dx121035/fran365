package com.example.fran365.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@RequestMapping("/item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public String create(@RequestParam Integer id,
                         @RequestParam String name,
                         @RequestParam Integer quantity,
                         @RequestParam int price
                         ){

        Item item = new Item();
        item.setProductId(id);
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setCreateDate(LocalDateTime.now());

        itemService.create(item);

        return "redirect:/product/readList";
    }
}
