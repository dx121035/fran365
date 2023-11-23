package com.example.fran365.delivery;

import java.util.List;

public interface DeliveryService {

    void create(String uid);

    List<Delivery> readList();

    Delivery readDetail(Integer id);
}
