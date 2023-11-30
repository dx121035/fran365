package com.example.fran365.sales;

import com.example.fran365.brand.Brand;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int month;

    private int income;

    @ManyToOne
    private Brand brand;

}
