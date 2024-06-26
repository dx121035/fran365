package com.example.fran365.resource;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ResourceService {
    List<Resource> readList();

    Resource readDetail(Integer id);

    void create(Resource resource);

    void update(Resource resource);

    void delete(Integer id);

    Page<Resource> getList(int page);

    void updateProductStock(Integer id, int amount);


}


