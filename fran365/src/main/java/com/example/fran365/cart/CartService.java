package com.example.fran365.cart;

import com.example.fran365.member.Member;

public interface CartService {

    void create(Member member);

    Cart readDetailUsername();

    void delete();

    Cart updateQuantity(int itemId, Integer quantity);

    int TotalPrice(Cart updatedCart);
}
