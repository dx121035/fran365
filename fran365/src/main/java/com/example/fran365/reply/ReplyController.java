/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.reply;


import com.example.fran365.board.Board;
import com.example.fran365.board.BoardService;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${aws.s3.awspath}")
    private String awspath;

    @Autowired
    private BoardService boardService;

    @PostMapping("/create")
    public String create(@RequestParam Integer id,  @RequestParam String content, @RequestParam String writer) {
        replyService.create(id,content,writer);
        if (writer.equals("관리자")) {

        	return "redirect:/admin/questionReadDetail?id=" + id;
        }
        return "redirect:/board/detail?id=" + id;
    }

    @PostMapping("/noticeCreate")
    public String noticeCreate(@RequestParam Integer id,  @RequestParam String content, @RequestParam String writer) {
        replyService.create(id,content,writer);

        if (writer.equals("관리자")) {

        	return "redirect:/admin/noticeReadDetail?id=" + id;
        }
        return "redirect:/board/noticeDetail?id=" + id;
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Integer id) {

        Board board = boardService.detail(id);
        Member member = memberService.readDetailUsername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        return "board/detail";
    }
    @GetMapping("/noticeDetail")
    public String noticeDetail(Model model,@RequestParam Integer id) {

        Board board = boardService.detail(id);
        Member member = memberService.readDetailUsername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        return "board/noticeDetail";

    }
}
