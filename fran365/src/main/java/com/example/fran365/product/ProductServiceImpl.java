/*
     name : SEULA LEE
     date : 2023/11/22
     mail : leeseulaseula@gmail.com
*/
package com.example.fran365.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    @Override
    public void create(Product product, MultipartFile multipartFile) throws IOException {

        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

        file.delete();


        product.setImage(filename);
        product.setCreateDate(LocalDateTime.now());

        productRepository.save(product);

    }

    @Override
    public Page<Product> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return productRepository.findAll(pageable);
    }


    @Override
    public Product readDetail(Integer id) {
        Optional<Product> op = productRepository.findById(id);

        return op.get();
    }

    @Override
    public void update(Product product, MultipartFile multipartFile) throws IOException {
        String filecheck = multipartFile.getOriginalFilename();

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
        productRepository.save(product);


        }

    @Override
    public void delete(Integer id) {
        Optional<Product> op = productRepository.findById(id);

        productRepository.delete(op.get());

    }
}
