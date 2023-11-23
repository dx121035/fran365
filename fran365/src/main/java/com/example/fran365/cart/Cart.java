package com.example.fran365.cart;

import com.example.fran365.item.Item;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
