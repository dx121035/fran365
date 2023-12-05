/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int price;

    private String image;

    private LocalDateTime createDate;


}
