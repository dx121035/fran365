package com.example.fran365.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;




    @Override
    public void create(String seller, String purchaser, String stockName, int quantity){

        History history = new History();

        history.setSeller(seller);
        history.setPurchaser(purchaser);
        history.setStockName(stockName);
        history.setQuantity(quantity);

        historyRepository.save(history);
    }

    @Override
    public List<History> getSellList(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        return historyRepository.findBySellerContaining(username);
    }

    @Override
    public List<History> getPurchaseList(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        return historyRepository.findByPurchaserContaining(username);
    }

    @Override
    public History update(History history){

        return historyRepository.save(history);
    }

}
