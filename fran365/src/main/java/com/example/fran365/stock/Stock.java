package com.example.fran365.stock;

import com.example.fran365.brand.Brand;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int quantity;

    @ManyToOne
    private Brand brand;
}
