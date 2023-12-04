package com.example.fran365.stock;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.member.Member;
import com.example.fran365.resource.Resource;
import com.example.fran365.resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void trade(Integer id, int amount, String category) {

        //username 추출
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        //작성자 brand 추출
        Optional<Resource> or = resourceRepository.findById(id);
        Resource resource = or.get();

        //판매자 brand 추출
        Brand seller = brandRepository.findByUsername(resource.getUsername());

        System.out.println(seller.getId());
        //구매자 brand 추출
        Brand purchaser = brandRepository.findByUsername(username);

        List<Stock> stockList = seller.getStockList();

        List<Stock> purchaserStockList = purchaser.getStockList();

        for (Stock stock : stockList) {
            if (stock.getBrand().getId().equals(seller.getId()) && stock.getName().equals(category)) {
                int updatedQuantity = stock.getQuantity() - amount;
                stock.setQuantity(updatedQuantity);
                stockRepository.save(stock);
                // 추가 작업이 필요하다면 여기에 추가
            }
        }

        for (Stock stock : purchaserStockList) {
            if (stock.getBrand().getId().equals(purchaser.getId()) && stock.getName().equals(category)) {
                int updatedQuantity = stock.getQuantity() + amount;
                stock.setQuantity(updatedQuantity);
                stockRepository.save(stock);
                // 추가 작업이 필요하다면 여기에 추가
            }
        }
    }
}
