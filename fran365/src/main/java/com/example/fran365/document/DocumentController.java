package com.example.fran365.document;




import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;



@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MemberService memberService;



    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());


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
    public String createtemp(Model model) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());


        return "document/createtemp";
    }

    @PostMapping("/createtemp")
    public String createtemp(Document document,
                         @RequestParam("filename") MultipartFile file
    ) throws IOException {

        documentService.createtemp(document, file);

        return "redirect:/";
    }








    @GetMapping("/readList")
    public String readList(Model model, Authentication authentication) {
        model.addAttribute("docus", documentService.readList());
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        String receiver = authentication.getName();
        model.addAttribute("count1", documentService.findByStatusAndReceiver(1,receiver));
        model.addAttribute("count2", documentService.findByStatusAndReceiver(2,receiver));
        model.addAttribute("count3", documentService.findByStatusAndReceiver(3,receiver));
        model.addAttribute("count4", documentService.findByStatusAndReceiver(4,receiver));
        model.addAttribute("count0", documentService.findByStatusAndReceiver(0,receiver));


        return "document/readList";
    }


    @GetMapping("/readListTemp")
    public String readListTemp(Model model) {
        model.addAttribute("docus", documentService.readListTemp());
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

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
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("docu", documentService.readDetail(id));

        return "document/readDetailTemp";
    }
    @GetMapping("/update")
    public String update(Model model,@RequestParam Integer id) {
        Document document = documentService.readDetail(id);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("docu", documentService.readDetail(id));
        return "document/update";
    }



    @PostMapping("/update")
    public String update(Document document,
                         @RequestParam("receiver") String receiver,
                         @RequestParam("filename") MultipartFile filename
    ) throws IOException {

        documentService.update(document, filename,receiver);

        return "redirect:/document/readList";
    }


    @GetMapping("/updateTemp")
    public String updateTemp(Model model,@RequestParam Integer id) {
        Document document = documentService.readDetail(id);
        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("docu", documentService.readDetail(id));
        return "document/update";
    }



    @PostMapping("/updateTemp")
    public String updateTemp(Document document,
                         @RequestParam("receiver") String receiver,
                         @RequestParam("filename") MultipartFile filename
    ) throws IOException {

        documentService.update(document, filename,receiver);

        return "redirect:/document/readList";
    }




    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        documentService.delete(id);
        return "redirect:/document/readList";
    }


    // 결재 버튼 처리
    @PostMapping("/approval/{documentId}")
    public String approval(@PathVariable("documentId") Integer documentId,@RequestParam("receiver") String receiver) {
        documentService.updateDocumentStatus(documentId, 1,receiver);
        return "redirect:/document/readList";
    }

    // 반려 버튼 처리
    @PostMapping("/reject/{documentId}")
    public String reject(@PathVariable("documentId") Integer documentId) {
        documentService.updateDocumentStatusReject(documentId, 100);
        return "redirect:/document/readList";
    }


    @GetMapping("/sendEmail")
    public String sendEmail() {
        return "member/sendEmail";
    }


    @Transactional
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam("username") String username) {
        try {
            memberService.sendTemporaryPassword(username);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }






    }








