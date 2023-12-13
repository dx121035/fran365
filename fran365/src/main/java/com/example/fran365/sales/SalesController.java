package com.example.fran365.sales;


import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequestMapping("/sales")
@Controller
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private SalesRepository salesRepository;

    @Value("${aws.s3.awspath}")
    private String awspath;

    @Autowired
    private MemberService memberService;

    @GetMapping("/update")
    public String update(@RequestParam Integer brand_id, Model model){

        Sales sales = salesService.findTopId(brand_id);


        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("sales", sales);

        return "sales/update";
    }

    @PostMapping("/update")
    public String update(Sales sales){

        System.out.println(sales.getId());
        salesService.update(sales);
        return "redirect:/resource/readList";
    }



}
