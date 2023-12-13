package com.example.fran365.social;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
@RequestMapping("/social")
public class SocialController {

    @Value("${aws.s3.awspath}")
    private String awspath;

    @Autowired
    private SocialService socialService;

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private MemberService memberService;


    @GetMapping("/main")
    public String allSocial(Model model){
        model.addAttribute("details", socialService.readDetail());
        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("lists", socialService.readList());

        return "social/main";
    }

    @PostMapping("/create")
    public String create(Social social){
        socialService.create(social);

        return "redirect:/social/main";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam String content,
                         @RequestParam String status){

        socialService.update(id,content,status);

        return "redirect:/social/main";
    }

    @PostMapping("/feedStatus")
    @ResponseBody
    public void updateStatus(Integer postId, String status) {

        socialService.updateStatus(postId, status);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like")
    public String like(@RequestParam Integer id) {
        Optional<Social> os = socialRepository.findById(id);
        Social social = os.get();
        Member member = memberService.readDetailUsername();
        socialService.like(social, member);

        return "redirect:/social/main?id=" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        socialService.delete(id);
        return "redirect:/social/main";
    }
}
