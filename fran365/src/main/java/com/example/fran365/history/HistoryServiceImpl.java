package com.example.fran365.history;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private BrandRepository brandRepository;




    @Override
    public void create(String seller, String purchaser, String stockName, int quantity, int price){

        History history = new History();

        history.setSeller(seller);
        history.setPurchaser(purchaser);
        history.setStockName(stockName);
        history.setQuantity(quantity);

        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");

        String formattedDate = date.format(formatter);

        history.setCreateDate(formattedDate);

        history.setPrice(price * quantity);

        historyRepository.save(history);
    }

    @Override
    public List<History> getSellList(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Brand brand = brandRepository.findByUsername(username);

        return historyRepository.findBySellerContaining(brand.getTitle());
    }

    @Override
    public List<History> getPurchaseList(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Brand brand = brandRepository.findByUsername(username);

        return historyRepository.findByPurchaserContaining(brand.getTitle());
    }

    @Override
    public History update(History history){

        return historyRepository.save(history);
    }

}
