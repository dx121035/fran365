package com.example.fran365.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Override
    public void create(Sales sales) {

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
}
