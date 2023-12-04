package com.example.fran365.document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String depart;

    private String category;

    private String status;

    private String image;

    private String reason;

    private String deadline;

    private LocalDateTime createDate;

                                            //private String position;

}
