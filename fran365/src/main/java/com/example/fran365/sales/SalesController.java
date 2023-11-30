package com.example.fran365.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sales")
@Controller
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping("/create")
    public String create(){

        return"sales/create";
    }

    @PostMapping("/create")
    public String create(Sales sales){

        salesService.create(sales);

        return "redirect:resource/readlist";

    }


}
