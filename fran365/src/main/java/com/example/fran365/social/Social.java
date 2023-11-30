package com.example.fran365.social;

import com.example.fran365.comment.Comment;
import com.example.fran365.member.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "social", cascade = CascadeType.REMOVE)
    private
    List<Comment> commentList;

    //추천  조아요
    @ManyToMany
    List<Member> voter;

}