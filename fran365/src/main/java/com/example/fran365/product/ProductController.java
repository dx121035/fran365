/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/

package com.example.fran365.product;

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

@Controller
@RequestMapping("/product")
public class ProductController {

    @Value("${aws.s3.awspath}")
    private String awspath;
    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String create() {

        return "product/create";
    }

    @PostMapping("/create")
    public String create(Product product,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        productService.create(product, file);

        return "redirect:/product/readList";
    }


    @GetMapping("/readList")
    public String readList(Model model, @RequestParam(value="page", defaultValue="0") int page) {

        Page<Product> paging = productService.getList(page);
        model.addAttribute("paging", paging);
        model.addAttribute("awspath", awspath);

        return "product/readList";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("details", productService.readDetail(id));
        return "product/update";
    }

    @PostMapping("/update")
    public String update(Product product,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        productService.update(product, file);

        return "redirect:/product/readList";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        productService.delete(id);
        return "redirect:/product/readList";
    }

}