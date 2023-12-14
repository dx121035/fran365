package com.example.fran365.history;

import java.util.List;

public interface HistoryService {

    void create(String seller, String purchaser, String stockName, int quantity, int price);

    List<History> getSellList();

    List<History> getPurchaseList();

    History update(History history);
}
