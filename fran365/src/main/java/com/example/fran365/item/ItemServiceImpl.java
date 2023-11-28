package com.example.fran365.item;

import com.example.fran365.cart.Cart;
import com.example.fran365.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartService cartService;


    @Override
    public void create(Item item) {

        Cart cart = cartService.readDetailUsername();

        item.setCart(cart);

        itemRepository.save(item);
    }

}
