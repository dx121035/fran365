package com.example.fran365.item;

import com.example.fran365.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT SUM(i.price) FROM Item i WHERE i.cart = :cart")
    Integer priceTotal(@Param("cart") Cart cart);
}
