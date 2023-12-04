package com.example.fran365.document;



import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface DocumentService {

    void create(Document document, MultipartFile multipartFile) throws IOException;



    Page<Document> getList(int page);
}
