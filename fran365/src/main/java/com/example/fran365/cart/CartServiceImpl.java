package com.example.fran365.cart;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;


    @Override
    public void create(Member member) {

        Cart cart = new Cart();

        cart.setUsername(memer.getUsername());

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
        Integer id = cart.getId();

        cartRepository.deleteById(id);
    }
}
