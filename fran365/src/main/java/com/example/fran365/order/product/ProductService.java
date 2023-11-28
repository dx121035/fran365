/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.order.product;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void create(Product product, MultipartFile multipartFile) throws IOException;

    List<Product> readList();

    Product readDetail(Integer id);

    void update(Product product, MultipartFile multipartFile) throws IOException;

    void delete(Integer id);

}
