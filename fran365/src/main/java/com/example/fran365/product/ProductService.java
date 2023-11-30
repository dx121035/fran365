/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.product;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void create(Product product, MultipartFile multipartFile) throws IOException;

    Page<Product> getList(int page);

    Product readDetail(Integer id);

    void update(Product product, MultipartFile multipartFile) throws IOException;

    void delete(Integer id);

}
