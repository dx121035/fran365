/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.reply;


import com.example.fran365.board.Board;
import com.example.fran365.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/reply")
@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/create")
    public String create(@RequestParam Integer id, @RequestParam String rwriter, @RequestParam String content, Principal principal) {

        replyService.create(id, content);
        replyService.sendSimpleMessage(rwriter,
                "질문에 답변이 등록되었습니다.",
                "답변:" + content);
        return "redirect:/board/detail?id=" + id;
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Integer id) {

        Board board = boardService.detail(id);
        Member member = memberService.readdetailusername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        return "board/detail";
    }
}
