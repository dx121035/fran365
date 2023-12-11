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

    private String category;

    // 0:임시저장  1:기본값 (결재시 1씩 증가),(반려시 100)
    private Integer status;

    private String image;

    private String reason;

    private String deadline;

    private  String receiver;

    private String comment;

    private  String title;


    private LocalDateTime createDate;

                                            //private String position;

}
