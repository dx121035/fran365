package com.example.fran365.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public String create(@RequestParam Integer id,
                         @RequestParam String content) {

        commentService.create(id, content);

        return "redirect:/social/social;?id=" + id;
    }
}