package com.example.fran365.comment;

import com.example.fran365.comment2.Comment2;
import com.example.fran365.social.Social;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Social social;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Comment2> comment2List;

    private String image;
}
