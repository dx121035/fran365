package com.example.fran365.sales;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private MemberService memberService;

    @Override
    public void create() {

        Sales sales = new Sales();

        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM");

        String formattedDate = date.format(formatter);

        sales.setDate(formattedDate);

        sales.setBrand(brandRepository.findByUsername(memberService.findUsername()));

        int income = 0;
        sales.setIncome(income);

        salesRepository.save(sales);

    }

    @Override
    public List<Sales> readlist(int brand_id) {

        List<Sales> sale = salesRepository.findByBrand_id(brand_id);

        return sale;

    }

    @Override
    public void update(Sales sales) {


        salesRepository.save(sales);
    }

    @Override
    public void delete(Integer id) {

        Optional<Sales> os = salesRepository.findById(id);
        Sales sales = os.get();

        salesRepository.delete(sales);

    }

    @Override
    public Sales findTopId(Integer brand_id) {

        List<Sales> salesList = salesRepository.findTopByBrandIdOrderBySalesIdDesc(brand_id);

        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM");

        String formattedDate = date.format(formatter);

        if(!salesList.get(0).getDate().equals(formattedDate) ){

            create();
        }

        return salesList.get(0);
    }

}
