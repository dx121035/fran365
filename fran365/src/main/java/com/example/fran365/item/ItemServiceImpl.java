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

        Item check = itemRepository.findByCartAndProductId(cart, item.getProductId());

        if (check != null){
            check.setQuantity(check.getQuantity() + item.getQuantity());

            itemRepository.save(check);
        } else{
            item.setCart(cart);

            itemRepository.save(item);
        }
    }

}
