/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/
package com.example.fran365.brand;


import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/brand")
@Controller
public class BrandController {

    @Autowired private BrandService brandService;
    @Autowired private MemberService memberService;
    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "brand/create";
    }

    @PostMapping("/create")
    public String create(Brand brand) {
        brandService.create(brand);
        return "redirect:/brand/list";
    }
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("brand",brandService.list());
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "brand/list";
    }
    @GetMapping("/detail")
    public String detail(Model model,@RequestParam Integer id) {

        Brand brand = brandService.detail(id);

        model.addAttribute("brand", brandService.detail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "brand/detail";
    }
    @GetMapping("/update")
    public String update(Model model,@RequestParam Integer id) {
        Brand brand = brandService.detail(id);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("brand", brandService.detail(id));
        return "brand/update";
    }
    @PostMapping("/update")
    public String update(Brand brand) {

        brandService.update(brand);
        return "redirect:/brand/list";
    }

    @GetMapping("/delete")
    public String delete(Model model,@RequestParam Integer id) {
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        brandService.delete(id);
        return "redirect:/brand/list";
    }
}
