package com.example.fran365.document;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.example.fran365.product.Product;
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

@Service
public class DocumentServiceImpl implements DocumentService{

//    @Autowired
//    private AmazonS3 amazonS3;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("bucket-va1rkc")
    private String bucketName;

    @Autowired
    private DocumentRepository documentRepository;



    @Override
    public void create(Document docuemnt, MultipartFile multipartFile) throws IOException {

        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

        file.delete();


        docuemnt.setImage(filename);
        docuemnt.setCreateDate(LocalDateTime.now());

        documentRepository.save(docuemnt);

    }



    @Override
    public Page<Document> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return documentRepository.findAll(pageable);
    }
}
