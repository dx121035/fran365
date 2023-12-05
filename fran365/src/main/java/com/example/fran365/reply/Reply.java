/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.reply;

import com.example.fran365.board.Board;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private String writer;

    private LocalDateTime createDate;

    @ManyToOne
    private Board board;
}
