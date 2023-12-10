package com.example.fran365.stock;

import com.example.fran365.brand.BrandRepository;
import com.example.fran365.brand.BrandService;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/stock")
@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private MemberService memberService;


    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/stockUpdate")
    public String update(Model model, @RequestParam Integer brand_id){

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("stocks", stockRepository.findByBrandId(brand_id));
        return "stock/stockUpdate";
    }

    @PostMapping("/stockUpdate")
    public String update(StockList stockList) {

        ArrayList<Stock> list = (ArrayList<Stock>) stockList.getStockList();
        stockService.update(list);
        return "redirect:/resource/readList";
    }

}
