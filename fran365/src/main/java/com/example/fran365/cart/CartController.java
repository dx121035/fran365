package com.example.fran365.cart;

import com.example.fran365.item.Item;
import com.example.fran365.item.ItemService;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/readDetail")
    public String readDetail(Model model){

        Cart cart = cartService.readDetailUsername();
        List<Item> item = cart.getItemList();

        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("cart", cartService.readDetailUsername());
        model.addAttribute("total", cartService.TotalPrice(cart));
        return "cart/readDetail";
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateQuantity(@RequestParam int itemId, @RequestParam Integer quantity) {
        Cart updatedCart = cartService.updateQuantity(itemId, quantity);
        int total = cartService.TotalPrice(updatedCart);

        Map<String, Object> response = new HashMap<>();
        response.put("total", total);

        return response;
    }

}
