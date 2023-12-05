package com.example.fran365.stock;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberService;
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

    @Autowired
    private MemberService memberService;

    @Override
    public void trade(Integer id, int amount, String category) {

        String username = memberService.findUsername();


        Optional<Resource> or = resourceRepository.findById(id);
        Resource resource = or.get();

        System.out.println(resource.getId());

        //판매자 brand 추출
        Brand seller = brandRepository.findByUsername(resource.getUsername());

        //구매자 brand 추출
        Brand purchaser = brandRepository.findByUsername(username);

        List<Stock> stockList = seller.getStockList();

        List<Stock> purchaserStockList = purchaser.getStockList();

        for (Stock stock : stockList) {
            // 판매자의 id를 갖고 있는 재고 찾기
            if (stock.getBrand().getId().equals(seller.getId()) && stock.getName().equals(category)) {
                // 현재 재고 - 판매한 재고
                int updatedQuantity = stock.getQuantity() - amount;
                stock.setQuantity(updatedQuantity);
                stockRepository.save(stock);

            }
        }

        for (Stock stock : purchaserStockList) {
            //구매자의 id를 갖고 있는 재고 찾기
            if (stock.getBrand().getId().equals(purchaser.getId()) && stock.getName().equals(category)) {
                // 현재 재고 + 구매한 재고
                int updatedQuantity = stock.getQuantity() + amount;
                stock.setQuantity(updatedQuantity);
                stockRepository.save(stock);

            }
        }
    }

    @Override
    public void update(List<Stock> updateStocks) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Brand brand = brandRepository.findByUsername(username);
        List<Stock> stocks = stockRepository.findByBrandId(brand.getId());

        for (Stock inven : stocks) {
            for (Stock updateStock : updateStocks) {


                if (inven.getName().equals(updateStock.getName())) {

                    inven.setQuantity(updateStock.getQuantity());
                    stockRepository.save(inven);
                }
            }

        }
    }
}
