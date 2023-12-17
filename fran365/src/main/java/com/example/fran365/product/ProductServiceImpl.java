/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.fran365.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private AmazonS3 amazonS3;

    @Value("bucket-va1rkc")
    private String bucketName;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockService stockService;

    @Override
    public void create(String name, int price, MultipartFile multipartFile) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

        file.delete();


        product.setImage(filename);
        product.setCreateDate(LocalDateTime.now());

        // 제품 생성 시
        stockService.newStock(product.getName());

        productRepository.save(product);

    }

    @Override
    public List<Product> readList() {

       return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }


    @Override
    public Product readDetail(Integer id) {
        Optional<Product> op = productRepository.findById(id);

        return op.get();
    }

    @Override
    public void update(String name, int price, Integer id, MultipartFile multipartFile) throws IOException {
        String filecheck = multipartFile.getOriginalFilename();

        Optional<Product> op = productRepository.findById(id);
        Product product = op.get();
        product.setName(name);
        product.setPrice(price);

        if (filecheck != null && !filecheck.trim().isEmpty()) {


            File file = new File(multipartFile.getOriginalFilename());

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }
            String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

            file.delete();

            product.setImage(filename);
        }
        product.setCreateDate(LocalDateTime.now());
        productRepository.save(product);


        }

    @Override
    public void delete(Integer id) {

        System.out.println("서비스 :" + id);
        
        Optional<Product> op = productRepository.findById(id);
        

        productRepository.delete(op.get());

    }
}
