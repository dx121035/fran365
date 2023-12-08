package com.example.fran365.document;




import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/create")
    public String create() {



        return "document/create";
    }

    @PostMapping("/create")
    public String create(Document document,
                         @RequestParam("receiver") String receiver,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        documentService.create(document, file,receiver);

        return "redirect:/";
    }



    @GetMapping("/createtemp")
    public String createtemp() {



        return "document/createtemp";
    }

    @PostMapping("/createtemp")
    public String createtemp(Document document,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        documentService.createtemp(document, file);

        return "redirect:/";
    }



//    @GetMapping("/readList")
//    public String readList(Model model, @RequestParam(value="page", defaultValue="0") int page) {
//
//        Page<Document> paging = documentService.getList(page);
//        model.addAttribute("paging", paging);
//
//
//        return "document/readList";
//    }

    @GetMapping("/readList")
    public String readList(Model model) {
        model.addAttribute("docus", documentService.readList());


        return "document/readList";
    }


    @GetMapping("/readDetail")
    public String detail(Model model,@RequestParam Integer id) {

        Document document = documentService.readDetail(id);

        model.addAttribute("docu", documentService.readDetail(id));

        return "document/readDetail";
    }
    @GetMapping("/update")
    public String update(Model model,@RequestParam Integer id) {
        Document document = documentService.readDetail(id);

        model.addAttribute("docu", documentService.readDetail(id));
        return "document/update";
    }
    @PostMapping("/update")
    public String update(Document document) {

        documentService.update(document);
        return "redirect:/document/readList";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        documentService.delete(id);
        return "redirect:/document/readList";
    }




    }





