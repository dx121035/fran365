/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.product;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void create(String name, int price, MultipartFile multipartFile) throws IOException;

    List<Product> readList();

    Product readDetail(Integer id);

    void update(String name, int price, Integer id, MultipartFile multipartFile) throws IOException;

    void delete(Integer id);

}
