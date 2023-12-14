package com.example.fran365.history;

import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MemberService memberService;

    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/salesList")
    public String getSalesList(Model model){

        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("history", historyService.getSellList());
        model.addAttribute("sell", historyService.getPurchaseList());

        return "history/salesList";
    }

    @GetMapping("/purchaseList")
    public String getPurchaseList(Model model){

        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("history", historyService.getPurchaseList());
        model.addAttribute("sell", historyService.getSellList());

        return "history/purchaseList";
    }

    @GetMapping("/update")
    public String update(Model model, Integer id){

        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("history", historyService.getSellList());

        return "history/update";
    }
}
