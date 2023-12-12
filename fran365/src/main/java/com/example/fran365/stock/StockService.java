package com.example.fran365.stock;

import java.util.List;

public interface StockService {

    void create();
    void trade(Integer id, int amount, String category);

    void update(List<Stock> updateStocks);

    void resourceUpdate(Stock foundStock);
}
