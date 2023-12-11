package com.example.fran365.document;




import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;



@Controller
@RequestMapping("/document")
public class DocumentController {

    @Value("${aws.s3.awspath}")
    private String awspath;

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
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "document/readList";
    }


    @GetMapping("/readListTemp")
    public String readListTemp(Model model) {
        model.addAttribute("docus", documentService.readListTemp());


        return "document/readListTemp";
    }


    @GetMapping("/readDetail")
    public String readDetail(Model model,@RequestParam Integer id) {

        Document document = documentService.readDetail(id);

        model.addAttribute("docu", documentService.readDetail(id));
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "document/readDetail";
    }


    @GetMapping("/readDetailTemp")
    public String readDetailTemp(Model model,@RequestParam Integer id) {

        Document document = documentService.readDetail(id);

        model.addAttribute("docu", documentService.readDetail(id));

        return "document/readDetailTemp";
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


    @GetMapping("/updateTemp")
    public String updateTemp(Model model,@RequestParam Integer id) {
        Document document = documentService.readDetail(id);

        model.addAttribute("docu", documentService.readDetail(id));
        return "document/updateTemp";
    }
    @PostMapping("/updateTemp")
    public String updateTemp(Document document) {

        documentService.update(document);
        return "redirect:/document/readList";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        documentService.delete(id);
        return "redirect:/document/readList";
    }


    // 결재 버튼 처리
    @PostMapping("/approval/{documentId}")
    public String approval(@PathVariable("documentId") Integer documentId) {
        documentService.updateDocumentStatus(documentId, 1);
        return "redirect:/document/readList";
    }

    // 반려 버튼 처리
    @PostMapping("/reject/{documentId}")
    public String reject(@PathVariable("documentId") Integer documentId) {
        documentService.updateDocumentStatus(documentId, 100);
        return "redirect:/document/readList";
    }






    }








