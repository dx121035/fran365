package com.example.fran365.cart;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
    private List<Item> itemList;
}
