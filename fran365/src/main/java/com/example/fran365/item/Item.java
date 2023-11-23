package com.example.fran365.item;

import com.example.fran365.cart.Cart;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int price;

    private String productId;

    private LocalDateTime createDate;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Member member;
}
