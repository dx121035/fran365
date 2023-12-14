package com.example.fran365.history;

import com.example.fran365.sales.Sales;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String seller;

    private String purchaser;

    private String content;

    private String stockName;

    private int quantity;

    private int recommend;

    private int price;

    private String createDate;
}
