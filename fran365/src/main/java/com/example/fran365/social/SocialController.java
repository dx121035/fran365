package com.example.fran365.social;

import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @Autowired
    private MemberService mamberService;

    @GetMapping("/social")
    public String allSocial(Model model){

        model.addAttribute("details", socialService.readDetail());
        model.addAttribute("lists", socialService.readList());

        return "social/social";
    }

    @PostMapping("/create")
    public String create(Social social){
        socialService.create(social);

        return "redirect:/social/social";
    }

    @PostMapping("/feedStatus")
    @ResponseBody
    public void updateStatus(Integer postId, String status) {

            socialService.updateStatus(postId, status);

    }

}
