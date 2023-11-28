/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/

package com.example.fran365.order.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/order/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String create() {
        return "order/product/create";
    }

    @PostMapping("/create")
    public String create(Product product,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        productService.create(product, file);

        return "redirect:/order/product/readList";
    }


    @GetMapping("/readList")
    public String readList(Model model) {

        model.addAttribute("lists", productService.readList());

        return "order/product/readList";
    }


    @GetMapping("/readDetail")
    public String readDetail(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("details", productService.readDetail(id));

        return "order/product/readDetail";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("details", productService.readDetail(id));
        return "order/product/update";
    }

    @PostMapping("/update")
    public String update(Product product,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        productService.update(product, file);

        return "redirect:/order/product/readList";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        productService.delete(id);
        return "redirect:/order/product/readList";
    }

}