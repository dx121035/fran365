package com.example.fran365.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/delivery")
@Controller
public class DeliveryController {

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

        return  "delivery/readList";

    }

    @GetMapping("/readDetail")
    public String readDetail(Model model, @RequestParam Integer id){

        model.addAttribute("delivery", deliveryService.readDetail(id));

        return "delivery/readDetail";
    }
}