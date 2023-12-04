package com.example.fran365.document;


import com.example.fran365.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/create")
    public String create() {

        return "document/create";
    }

    @PostMapping("/create")
    public String create(Document document,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        documentService.create(document, file);

        return "redirect:/document/readList";
    }

    @GetMapping("/readList")
    public String readList(Model model, @RequestParam(value="page", defaultValue="0") int page) {

        Page<Document> paging = documentService.getList(page);
        model.addAttribute("paging", paging);


        return "product/readList";
    }




    }





