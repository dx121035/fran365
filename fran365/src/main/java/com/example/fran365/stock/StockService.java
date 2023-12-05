package com.example.fran365.stock;

import java.util.List;

public interface StockService {

    void trade(Integer id, int amount, String category);

    void update(List<Stock> stocks);
}
