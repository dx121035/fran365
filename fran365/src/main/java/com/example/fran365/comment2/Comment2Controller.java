package com.example.fran365.comment2;

import com.example.fran365.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/comment2")
@Controller
public class Comment2Controller {

    @Autowired
    private Comment2Service comment2Service;


    @PostMapping("/create")
    public String create(@RequestParam Integer cid,
                         @RequestParam Integer sid,
                         @RequestParam String content,
                         @RequestParam String image) {

        comment2Service.create(cid, content, image);

        return "redirect:/social/main?id=" + sid;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        comment2Service.delete(id);
        return "redirect:/social/main";
    }
}
