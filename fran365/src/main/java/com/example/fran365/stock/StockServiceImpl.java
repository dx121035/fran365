package com.example.fran365.stock;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.brand.BrandService;
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
   private MemberService memberService;

   @Autowired
   private StockRepository stockRepository;


    @Override
    public void create() {

        Brand currentBrand = brandRepository.findByUsername(memberService.findUsername());

        for (int i = 0; i < 5; i++) {
            // Stock 객체 생성
            Stock stock = new Stock();

            // 생성된 Stock 객체의 속성 설정
            stock.setQuantity(0);
            stock.setBrand(currentBrand);

            // name 설정
            switch (i) {
                case 0:
                    stock.setName("도우");
                    break;
                case 1:
                    stock.setName("페퍼로니");
                    break;
                case 2:
                    stock.setName("감자");
                    break;
                case 3:
                    stock.setName("올리브");
                    break;
                case 4:
                    stock.setName("토마토소스");
                    break;
                default:
                    break;
            }
            stockRepository.save(stock);

        }
    }
    @Override
    public void trade(Integer id, int amount, String category) {

        String username = memberService.findUsername();

        //구매자 brand 추출
        Brand purchaser = brandRepository.findByUsername(username);

        List<Stock> purchaserStockList = purchaser.getStockList();


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

                System.out.println(updateStock.getId());

                if (inven.getId().equals(updateStock.getId())) {

                    inven.setQuantity(updateStock.getQuantity());
                    stockRepository.saveAndFlush(inven);
                }
            }

        }
    }


    @Override
    public void resourceUpdate(Stock foundStock) {

        stockRepository.save(foundStock);
    }
}
