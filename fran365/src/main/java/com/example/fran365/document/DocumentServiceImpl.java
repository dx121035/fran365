package com.example.fran365.document;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


import com.example.fran365.member.MailDto;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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



    @Autowired
    private AmazonS3 amazonS3;

    @Value("bucket-va1rkc")
    private String bucketName;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MemberService memberService;



    @Override
    public void create(Document docuemnt, MultipartFile multipartFile, String receiver) throws IOException {

        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

        file.delete();


        docuemnt.setImage(filename);
        docuemnt.setStatus(1);
        docuemnt.setCreateDate(LocalDateTime.now());
        docuemnt.setReceiver(receiver);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        docuemnt.setSender(authentication.getName());
        

        documentRepository.save(docuemnt);

    }


    @Override
    public void createtemp(Document docuemnt, MultipartFile multipartFile) throws IOException {

        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file));

        file.delete();


        docuemnt.setImage(filename);
        docuemnt.setStatus(0);
        docuemnt.setReceiver(memberService.findUsername());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Document> sentDocuments = documentRepository.findByReceiver(username);
        List<Document> receivedDocuments = documentRepository.findBySender(username);
        sentDocuments.addAll(receivedDocuments);
        return sentDocuments;
    }

    @Override
    public int findByStatusAndReceiver(Integer status, String receiver) {
        List<Document> list = documentRepository.findByStatusAndReceiver(status,receiver);

        return list.size();
    }


    @Override
    public List<Document> readListTemp() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String receiver = authentication.getName();
        return documentRepository.findByStatusAndReceiver(0,receiver);
    }





    @Override
    public Document readDetail(Integer id) {
        Optional<Document> document =  documentRepository.findById(id);
        return document.get();
    }

    @Override
    public Document readDetailTemp(Integer id) {
        Optional<Document> document =  documentRepository.findById(id);
        return document.get();
    }



    @Override
    public void update(Document docuemnt, MultipartFile filename, String receiver) throws IOException {
        String filecheck = filename.getOriginalFilename();

        if (filecheck != null && !filecheck.trim().isEmpty()) {
            File file = new File(filename.getOriginalFilename());

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(filename.getBytes());
            }

            String tfilename = System.currentTimeMillis() + "_" + filename.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, tfilename, file));

            file.delete();


            docuemnt.setImage(tfilename);
            docuemnt.setStatus(1);
            docuemnt.setCreateDate(LocalDateTime.now());
            docuemnt.setReceiver(receiver);
        }
        documentRepository.save(docuemnt);

    }


//    @Override
//    public void updateTemp(Document document) {
//        document.setCreateDate(LocalDateTime.now());
//        documentRepository.save(document);
//    }

    @Override
    public void delete(Integer id) {
        Optional<Document> document =  documentRepository.findById(id);
        documentRepository.delete(document.get());
    }



    public void updateDocumentStatus(Integer documentId, int increment,String receiver) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        // status를 주어진 값만큼 증가시킴
        document.setStatus(document.getStatus() + increment);
        document.setReceiver(receiver);

        // 저장
        documentRepository.save(document);
    }


    public void updateDocumentStatusReject(Integer documentId, int increment ) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        // status를 주어진 값만큼 증가시킴
        document.setStatus(document.getStatus() + increment);



        // 저장
        documentRepository.save(document);
    }

    @Override
    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("seula724@naver.com");
        message.setReplyTo("seula724@naver.com");
        System.out.println("message" + message);
        mailSender.send(message);
    }



}
