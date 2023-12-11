/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.board;

import com.example.fran365.member.Member;
import com.example.fran365.reply.Reply;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Board{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition ="LONGTEXT")
    private String content;

    private String writer;

    private String category;

    private String status;
    
    private String username;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "board", cascade= CascadeType.REMOVE)
    private List<Reply> replyList;

    //게시물 조회수 //Set- 중복 불가능 List - 중복 가능
    @ManyToMany
    List<Member> hitter;
 }
