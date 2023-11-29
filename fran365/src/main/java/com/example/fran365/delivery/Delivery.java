package com.example.fran365.delivery;

import com.example.fran365.status.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uid;//카드 승인번호

    private String name;

    private String allAbout;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.REMOVE)
    private List<Status> statusList;

    private String username;

    private int total;

    private LocalDateTime createDate;
}
