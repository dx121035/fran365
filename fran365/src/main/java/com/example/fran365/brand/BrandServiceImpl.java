/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.brand;

import com.example.fran365.member.Member;
import com.example.fran365.member.MemberRepository;
import com.example.fran365.sales.SalesService;
import com.example.fran365.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SalesService salesService;

    @Autowired
    private StockService stockService;
    
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void create(Brand brand) {
        brand.setCreateDate(LocalDateTime.now());
        brandRepository.save(brand);
        
        String username = brand.getUsername();
        Brand brandByUsername = brandRepository.findByUsername(username);
        Integer brandId = brandByUsername.getId();
        Optional<Member> om = memberRepository.findByUsername(username);
        Member member = om.get();
        member.setBid(brandId);
        
        salesService.create();
        stockService.create();

    }

    @Override
    public List<Brand> list() {
        return brandRepository.findAll();
    }

    @Override
    public Brand detail(Integer id) {
        Optional< Brand > brand =  brandRepository.findById(id);
        return brand.get();
    }

    @Override
    public void update(Brand brand) {
        brand.setCreateDate(LocalDateTime.now());
        brandRepository.save(brand);
    }

    @Override
    public void delete(Integer id) {
        Optional<Brand> brand =  brandRepository.findById(id);
        brandRepository.delete(brand.get());
    }
}
