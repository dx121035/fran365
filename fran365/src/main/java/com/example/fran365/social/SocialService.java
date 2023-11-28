package com.example.fran365.social;

import com.example.fran365.product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SocialService {

    void create(Social social);

    List<Social> readList(String username);

    List<Social> readDetail();

    void update(Social social);

    void delete(Integer id);
}
