package com.example.fran365.document;




import com.example.fran365.member.MailDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface DocumentService {

    void create(Document document, MultipartFile multipartFile, String receiver) throws IOException;

    void createtemp(Document document, MultipartFile multipartFile) throws IOException;

  //  Page<Document> getList(int page);

    List<Document> readList();

    List<Document> readListTemp();

    Document readDetail(Integer id);

    Document readDetailTemp(Integer id);

    int findByStatusAndReceiver(Integer status, String receiver);




    void update(Document document, MultipartFile filename, String receiver) throws IOException;

  //  void updateTemp (Document document);

    void delete (Integer id);


    void updateDocumentStatus(Integer documentId, int increment,String receiver);

    void updateDocumentStatusReject(Integer documentId, int increment, String reason);


    void mailSend(MailDto mailDto);
}
