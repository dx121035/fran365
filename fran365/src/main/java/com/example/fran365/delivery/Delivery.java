package com.example.fran365.delivery;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uid;//카드 승인번호

    private String invoiceNumber;//송장 번호

    private String allAbout;

    private String status;

    private int step;

    private LocalDateTime createDate;
}
