package com.example.fran365.social;

import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/social")
public class SocialController {

    @Value("${aws.s3.awspath}")
    private String awspath;

    @Autowired
    private SocialService socialService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/social")
    public String allSocial(Model model){

        model.addAttribute("details", socialService.readDetail());
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("lists", socialService.readList());

        return "social/social";
    }

    @PostMapping("/create")
    public String create(Social social){
        socialService.create(social);

        return "redirect:/social/social";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam String content,
                         @RequestParam String status){

        socialService.update(id,content,status);

        return "redirect:/social/social";
    }

    @PostMapping("/feedStatus")
    @ResponseBody
    public void updateStatus(Integer postId, String status) {

        socialService.updateStatus(postId, status);

    }

}
