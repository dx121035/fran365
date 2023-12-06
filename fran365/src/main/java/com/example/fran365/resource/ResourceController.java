package com.example.fran365.resource;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.member.MemberRepository;
import com.example.fran365.member.MemberService;
import com.example.fran365.sales.Sales;
import com.example.fran365.sales.SalesRepository;
import com.example.fran365.sales.SalesService;
import com.example.fran365.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequestMapping("/resource")
@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SalesRepository salesRepository;

    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());

        return "resource/create";
    }

    @PostMapping("/create")
    public String create(Resource resource,  @RequestParam("filename") MultipartFile file) throws IOException {



        resourceService.create(resource, file);

        return "redirect:/resource/readList";
    }

    @GetMapping("/readList")
    public String readList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
//        String customLocalDateTimeFormat =LocalDate.now()
//                .format(DateTimeFormatter.ofPattern("yyyy-MM"));
//        System.out.println(customLocalDateTimeFormat);


       String username = memberService.findUsername();

        Brand brand = brandRepository.findByUsername(username);

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("brand",brand);
        model.addAttribute("sales", salesRepository.findByBrand_IdOrderByIdDesc(brand.getId()));
        model.addAttribute("member", memberService.readDetailUsername());

        Page<Resource> paging = resourceService.getList(page);
        model.addAttribute("paging", paging);


        return "resource/readList";
    }

    @GetMapping("/readDetail")
    public String readDetail(@RequestParam Integer id, Model model) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("resource", resourceService.readDetail(id));

       return "resource/readDetail";
    }

    @GetMapping("/update")
    public String update(@RequestParam Integer id, Model model) {

        Resource resource = resourceService.readDetail(id);

        model.addAttribute("awspath", awspath);
        model.addAttribute("member",memberService.readDetailUsername());
        model.addAttribute("resource", resource);

        return "resource/update";
    }

    @PostMapping("/update")
    public String update(Resource resource,  @RequestParam("filename") MultipartFile multipartFile) throws IOException {

        Integer id = resource.getId();

        resourceService.update(resource, multipartFile);

        return "redirect:/resource/readDetail?id=" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {

        resourceService.delete(id);

        return "redirect:/resource/readList";
    }

    @GetMapping("/purchase")
    public String purchase(@RequestParam Integer id, @RequestParam int amount, @RequestParam String category){

        resourceService.updateProductStock(id, amount);
        stockService.trade(id, amount, category);

        return "redirect:/resource/readList";
    }
}
