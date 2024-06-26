/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void create(Brand brand) {
        brand.setCreateDate(LocalDateTime.now());
        brandRepository.save(brand);
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
