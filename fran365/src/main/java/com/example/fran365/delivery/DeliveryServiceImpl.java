package com.example.fran365.delivery;

import com.example.fran365.cart.Cart;
import com.example.fran365.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ItemService itemService;

    @@Autowired
    private MemberSevice memberService;

    @Override
    public void create(String uid) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Cart cart = cartService.readDetailUsername();
        List<Item> item = cart.getItemList();
        String itemName = item.get(0).getName();

        Delivery delivery = new Delivery();
        delivery.setCreateDate(LocalDateTime.now());
        delivery.setStep(1);
        delivery.setUid(uid);
        delivery.setUsername(username);
        delivery.setInvoiceNumber(delivery.getInvoiceNumber());
        deliveryRepository.save(delivery);

        Member member = memberService.readDetailUsername();
        cartService.delete();
        cartService.create(user);

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
