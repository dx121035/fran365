/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/

package com.example.fran365.product;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Value("${aws.s3.awspath}")
    private String awspath;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;


    @GetMapping("/create")
    public String create(Model model, ProductCreateForm productCreateForm) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid ProductCreateForm productCreateForm, BindingResult bindingResult,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            return "product/create";
        }

        productService.create(productCreateForm.getName(), productCreateForm.getPrice(),file);

        return "redirect:/product/readList";
    }


    @GetMapping("/readList")
    public String readList(Model model, @RequestParam(value="page", defaultValue="0") int page) {

        Page<Product> paging = productService.getList(page);
        model.addAttribute("paging", paging);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "product/readList";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") Integer id, ProductCreateForm productCreateForm) {

        model.addAttribute("details", productService.readDetail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        return "product/update";
    }

    @PostMapping("/update")
    public String update(Model model, @Valid ProductCreateForm productCreateForm, BindingResult bindingResult, @RequestParam Integer id,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("details", productService.readDetail(id));
            return "product/update";
        }

        productService.update(productCreateForm.getName(), productCreateForm.getPrice(),id,file);

        return "redirect:/product/readList";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        productService.delete(id);
        return "redirect:/product/readList";
    }

}