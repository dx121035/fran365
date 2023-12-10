package com.example.fran365.resource;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String title;

    private String content;

    private String writer;

    private String username;

    private String address;

    private String category;

    private Integer amount;

    private int price;

    private String image;

    private String expiration;

    private LocalDateTime createDate;

}