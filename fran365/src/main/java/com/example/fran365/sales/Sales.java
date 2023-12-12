package com.example.fran365.sales;

import com.example.fran365.brand.Brand;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String date;

    private int income;

    @ManyToOne
    private Brand brand;


}
