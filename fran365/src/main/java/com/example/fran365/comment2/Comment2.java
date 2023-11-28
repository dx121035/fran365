package com.example.fran365.comment2;

import com.example.fran365.comment.Comment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Comment comment;
}
