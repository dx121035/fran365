package com.example.fran365.cart;

public interface CartService {

    void create(Member member);

    Cart readDetailUsername();

    void delete();
}
