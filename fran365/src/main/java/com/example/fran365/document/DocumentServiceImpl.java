package com.example.fran365.document;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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



//    @Override
//    public Page<Document> getList(int page) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createDate"));
//
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//        return documentRepository.findAll(pageable);
//    }


    @Override
    public List<Document> readList() {
        return documentRepository.findAll();
    }


    @Override
    public Document readDetail(Integer id) {
        Optional< Document > document =  documentRepository.findById(id);
        return document.get();
    }

    @Override
    public void update(Document document) {
        document.setCreateDate(LocalDateTime.now());
        documentRepository.save(document);
    }

    @Override
    public void delete(Integer id) {
        Optional<Document> document =  documentRepository.findById(id);
        documentRepository.delete(document.get());
    }
}
