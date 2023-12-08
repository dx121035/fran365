package com.example.fran365.delivery;

import com.example.fran365.member.MemberService;
import com.example.fran365.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/delivery")
@Controller
public class DeliveryController {

    @Value("${aws.s3.awspath}")
    private String awspath;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/pay")
    public String pay(String uid){
        deliveryService.create(uid);

        return "redirect:/delivery/readList";
    }

    @GetMapping("/readList")
    public String readList(Model model){

        model.addAttribute("deliveries", deliveryService.readList());
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return  "delivery/readList";

    }

    @GetMapping("/readDetail")
    public String readDetail(Model model, @RequestParam Integer id){

        model.addAttribute("delivery", deliveryService.readDetail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "delivery/readDetail";
    }
}