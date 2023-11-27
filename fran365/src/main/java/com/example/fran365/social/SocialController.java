package com.example.fran365.social;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiOptionPaneUI;
import java.io.IOException;

@Controller
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @Autowired
    private MemberService mamberService;

    @GetMapping("/create")
    public String create(){

        return "social/create";
    }

    @PostMapping("/create")
    public String create(Social social, @RequestParam("filename") MultipartFile file)
                        throws IOException {
        socialService.create(social, file);

        return "redirect:/product/readlist";
    }

    @GetMapping("/readlist")
    public String readList(Model model, String username){

        model.addAttribute("lists", socialService.readList(username));

        return "social/social";
    }


}
