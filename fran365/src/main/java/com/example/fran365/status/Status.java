package com.example.fran365.status;

import com.example.fran365.delivery.Delivery;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tracking;

    private LocalDateTime createDate;

    @ManyToOne
    private Delivery delivery;

    private String username;

    private String step;
}
