package com.example.fran365.delivery;


import com.example.fran365.cart.Cart;
import com.example.fran365.cart.CartService;
import com.example.fran365.item.Item;
import com.example.fran365.item.ItemService;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import com.example.fran365.status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CartService cartService;

    @Override
    public void create(String uid) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Cart cart = cartService.readDetailUsername();
        List<Item> itemList = cart.getItemList();

        StringBuilder deliveryItem = new StringBuilder();

        for(int i = 0; i < itemList.size(); i++){
            String itemName = itemList.get(i).getName();

            if(i != 0) {
                deliveryItem.append(", ");  // 아이템 이름들 사이에 쉼표와 공백을 추가합니다.
            }
            deliveryItem.append(itemName);
        }
        String name = deliveryItem.toString();




        int total = cartService.TotalPrice(cart);

        Delivery delivery = new Delivery();
        delivery.setCreateDate(LocalDateTime.now());
        delivery.setTotal(total);
        //delivery.setAllAbout(allAbout);
        delivery.setName(name);
        delivery.setUid(uid);
        delivery.setUsername(username);

        deliveryRepository.save(delivery);

        Status status = new Status();
        status.setStep(1);
        status.setUsername(username);
        status.setTracking("");
        status.setDelivery(delivery);
        statusService.create(status);

        Member member = memberService.readDetailUsername();
        cartService.delete();
        cartService.create(member);

    }

    @Override
    public List<Delivery> readList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return deliveryRepository.findByUsername(username);
    }

    @Override
    public Delivery readDetail(Integer id) {

        Optional<Delivery> od = deliveryRepository.findById(id);
        Delivery delivery = od.get();

        return delivery;
    }
}
