package com.example.fran365.sales;

import java.util.List;

public interface SalesService {

    void create();

    List<Sales> readlist(int brand_id);

    void update(Sales sales);

    void delete(Integer id);

    Sales findTopId(Integer brand_id);
}
