package com.example.fran365.document;




import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface DocumentService {

    void create(Document document, MultipartFile multipartFile) throws IOException;

    void createtemp(Document document, MultipartFile multipartFile) throws IOException;

  //  Page<Document> getList(int page);

    List<Document> readList();

    Document readDetail(Integer id);

    void update (Document document);

    void delete (Integer id);
}
