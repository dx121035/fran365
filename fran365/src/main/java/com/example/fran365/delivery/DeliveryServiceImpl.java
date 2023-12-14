package com.example.fran365.delivery;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.fran365.cart.Cart;
import com.example.fran365.cart.CartService;
import com.example.fran365.item.Item;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
import com.example.fran365.status.Status;
import com.example.fran365.status.StatusService;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private StatusService statusService;

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

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            String itemName = item.getName();
            int itemQuantity = item.getQuantity();

            if (i != 0) {
                deliveryItem.append(",  ");
            }

            deliveryItem.append(itemName).append(" (수량: ").append(itemQuantity).append("개)");
        }

        String allAbout = deliveryItem.toString();

        String firstItem = itemList.get(0).getName();

        int itemSize = itemList.size() - 1;

        String name;

        if (itemSize == 0) {
            name = firstItem;
        } else {
            name = firstItem + " 외 " + itemSize + "개";
        }

        int total = cartService.TotalPrice(cart);

        Delivery delivery = new Delivery();
        delivery.setCreateDate(LocalDateTime.now());
        delivery.setTotal(total);
        delivery.setAllAbout(allAbout);
        delivery.setName(name);
        delivery.setUid(uid);
        delivery.setUsername(username);

        deliveryRepository.save(delivery);

        Status status = new Status();
        status.setStep("결제완료");
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

        return deliveryRepository.findByUsername(username, Sort.by(Sort.Direction.DESC, "createDate"));
    }

    @Override
    public Delivery readDetail(Integer id) {

        Optional<Delivery> od = deliveryRepository.findById(id);
        Delivery delivery = od.get();

        return delivery;
    }
}
