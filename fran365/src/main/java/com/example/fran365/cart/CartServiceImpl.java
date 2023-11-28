package com.example.fran365.cart;


import com.example.fran365.item.Item;
import com.example.fran365.item.ItemService;
import com.example.fran365.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;


    @Override
    public void create(Member member) {

        Cart cart = new Cart();

        cart.setUsername(member.getUsername());

        cartRepository.save(cart);
    }

    @Override
    public Cart readDetailUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<Cart> oc = cartRepository.findByUsername(username);
        return oc.get();
    }

    @Override
    public void delete() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<Cart> oc = cartRepository.findByUsername(username);
        Cart cart = oc.get();

        cartRepository.delete(cart);
    }

    @Override
    public Cart updateQuantity(int itemId, Integer quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<Cart> oc = cartRepository.findByUsername(username);
        Cart cart = oc.get();

        List<Item> items = cart.getItemList();
        for(Item item : items) {
            if(item.getId() == itemId) {
                item.setQuantity(quantity);
                break;
            }
        }

        cartRepository.save(cart);
        return cart;
    }

    public int TotalPrice(Cart cart) {
        int total = 0;

        // 장바구니에 담긴 각 아이템에 대해
        for (Item item : cart.getItemList()) {
            // 아이템의 가격과 수량을 곱한 값을 total에 더함
            total += item.getPrice() * item.getQuantity();
        }

        // 총 금액 반환
        return total;
    }
}

