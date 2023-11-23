package com.example.fran365.cart;

import com.example.fran365.item.Item;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/readDetail")
    public String readDetail(Model model){

        Cart cart = cartService.readDetailUsername();
        List<Item> item = cart.getItemList();

        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("cart", cartService.readDetailUsername());

        return "cart/readDetail";
    }
}
