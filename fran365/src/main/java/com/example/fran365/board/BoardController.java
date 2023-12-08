/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.board;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/board")
@Controller
public class BoardController {

    @Autowired private BoardService boardService;
    @Autowired private MemberService memberService;
    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "board/create";
    }

    @PostMapping("/create")
    public String create(Board board, @RequestParam String category) throws IOException {
        boardService.create(board, category);

        // Redirect to the appropriate URL based on the category
        if ("공지".equals(category)) {
        	if (board.getWriter().equals("관리자")) {
            	
            	return "redirect:/admin/noticeReadList";
        	}
            return "redirect:/board/notice";
        } else if ("FAQ".equals(category)) {
            return "redirect:/board/FAQ";
        } else {
            return "redirect:/board/list";
        }
    }
    @GetMapping("/list")
    public String list(Model model) {
        List<String> categories = Arrays.asList("상품", "배송", "재고");
        List<Board> allBoards = boardService.getAllBoardsByCategories(categories);
        model.addAttribute("boards", allBoards);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("FAQ",boardService.readFAQList("FAQ"));

        return "board/list";
    }
    @GetMapping("/notice")
    public String notice(Model model,@RequestParam (value="page",defaultValue="0")int page) {
        Page<Board> paging = boardService.getNoticeBoards("공지",page);
        model.addAttribute("paging",paging);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "board/notice";
    }
    @GetMapping("/FAQ")
    public String FAQ(Model model,@RequestParam (value="page",defaultValue="0")int page) {
        Page<Board> paging = boardService.getNoticeBoards("FAQ",page);
        model.addAttribute("paging",paging);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "board/FAQ";
    }
    @GetMapping("/detail")
    public String detail(Model model,@RequestParam Integer id) {

        Board board = boardService.detail(id);
        Member member = memberService.readDetailUsername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

            return "board/detail";
    }
    @GetMapping("/noticeDetail")
    public String readDetail(Model model,@RequestParam Integer id) {

        Board board = boardService.detail(id);
        Member member = memberService.readDetailUsername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
            return "board/noticeDetail";

    }
    @GetMapping("/FAQDetail")
    public String FAQ(Model model,@RequestParam Integer id) {

        Board board = boardService.detail(id);
        Member member = memberService.readDetailUsername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "board/FAQDetail";

    }
    @GetMapping("/update")
    public String update(Model model,@RequestParam Integer id){
        Board board = boardService.detail(id);
        Member member = memberService.readDetailUsername();
        boardService.hit(board,member);

        model.addAttribute("board", boardService.detail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "board/update";
    }
    @PostMapping("/update")
    public String update(Board board, @RequestParam("category") String category) throws IOException {

        boardService.update(board, category);
        if ("공지".equals(category)) {
        	if (board.getWriter().equals("관리자")) {
            	
            	return "redirect:/admin/noticeReadDetail?id=" + board.getId();
        	}
            return "redirect:/board/notice";
        } else if ("FAQ".equals(category)) {
            return "redirect:/board/FAQ";
        } else {
            return "redirect:/board/list";
        }
    }

    @GetMapping("/delete")
    public String delete(Model model,@RequestParam Integer id,@RequestParam String category, String writer) {
        boardService.delete(id);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        if ("공지".equals(category)) {
        	if (writer.equals("관리자")) {
            	
            	return "redirect:/admin/noticeReadList";
        	}
            return "redirect:/board/notice";
        } else if ("FAQ".equals(category)) {
            return "redirect:/board/FAQ";
        } else {
            return "redirect:/board/list";
        }
    }
}
