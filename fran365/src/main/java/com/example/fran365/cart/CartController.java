package com.example.fran365.cart;

import com.example.fran365.item.Item;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/readDetail")
    public String readDetail(Model model){

        Cart cart = cartService.readDetailUsername();
        List<Item> itemList = cart.getItemList();

        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("cart", cartService.readDetailUsername());
        model.addAttribute("total", cartService.TotalPrice(cart));
        model.addAttribute("itemName", itemList.get(0).getName());
        model.addAttribute("itemSize", itemList.size() - 1);
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

    @PostMapping("/delete")
    @ResponseBody
    public void deleteItem(@RequestParam int itemId) {
        cartService.deleteItem(itemId);
    }
}