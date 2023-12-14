package com.example.fran365.resource;

import com.example.fran365.brand.Brand;
import com.example.fran365.brand.BrandRepository;
import com.example.fran365.member.MemberService;
import com.example.fran365.sales.Sales;
import com.example.fran365.sales.SalesRepository;
import com.example.fran365.sales.SalesService;
import com.example.fran365.stock.Stock;
import com.example.fran365.stock.StockRepository;
import com.example.fran365.stock.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private SalesRepository salesRepository;

    @Value("${aws.s3.awspath}")
    private String awspath;

    @GetMapping("/create")
    public String create(ResourceForm resourceForm) {

        return "resource/create";
    }

    @PostMapping("/create")
    public String create(@Valid ResourceForm resourceForm,
                         BindingResult bindingResult,
                         Resource resource,
                         @RequestParam("filename") MultipartFile file,
                         Model model) throws IOException {

        if (bindingResult.hasErrors()) {

            return "resource/create";
        }

        int requestedAmount = resource.getAmount();
        Brand brand = brandRepository.findByUsername(memberService.findUsername());
        List<Stock> stockList = brand.getStockList();

        int totalStockQuantity = 0;
        Stock foundStock = null;

        //현재 로그인한 브랜드의 해당 제품 재고량 가져오기
        for (Stock stock : stockList) {
            if (stock.getName().equals(resource.getCategory())) {
                totalStockQuantity += stock.getQuantity();

                // 변경할 재고
                foundStock = stock;

                System.out.println(totalStockQuantity);
            }
        }

        //게시글을 작성한 재고보다 현재 재고량이 적으면 에러 출력
        if (requestedAmount > totalStockQuantity) {
            bindingResult.rejectValue("amount", "amount.mismatch", "입력하신 양보다 현재 재고가 부족합니다.");

            return "resource/create";
        }

        // 재고 공유 글을 작성하였으므로 작성한 재고만큼 브랜드의 재고 감소
        int updateQuantity = totalStockQuantity - requestedAmount;
        foundStock.setQuantity(updateQuantity);
        stockService.resourceUpdate(foundStock);

        //재고 공유 게시글 작성
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

        Sales sales = salesService.findTopId(brand.getId());


        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("brand", brand);
        model.addAttribute("currentSales", sales);
        model.addAttribute("sales", salesRepository.findByBrand_IdOrderByIdDesc(brand.getId()));
        model.addAttribute("member", memberService.readDetailUsername());

        Page<Resource> paging = resourceService.getList(page);
        model.addAttribute("paging", paging);

        return "resource/readList";
    }

    @GetMapping("/readDetail")
    public String readDetail(@RequestParam Integer id, Model model) {

        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("resource", resourceService.readDetail(id));

        return "resource/readDetail";
    }

    @GetMapping("/update")
    public String update(@RequestParam Integer id, Model model) {

        Resource resource = resourceService.readDetail(id);

        model.addAttribute("awspath", awspath);
        model.addAttribute("member", memberService.readDetailUsername());
        model.addAttribute("resource", resource);

        return "resource/update";
    }

    @PostMapping("/update")
    public String update(Resource resource, @RequestParam("filename") MultipartFile multipartFile) throws IOException {

        Integer id = resource.getId();

        resourceService.update(resource, multipartFile);

        return "redirect:/resource/readDetail?id=" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id, int amount, String stockName) {

        resourceService.delete(id);

        Brand brand = brandRepository.findByUsername(memberService.findUsername());
        List<Stock> stockList = brand.getStockList();

        Stock foundStock = null;

        for (Stock stock : stockList) {
            if (stock.getName().equals(stockName)) {

                foundStock = stock;
                int updateQuantity = stock.getQuantity() + amount;
                foundStock.setQuantity(updateQuantity);
            }
        }
        stockService.resourceUpdate(foundStock);

        return "redirect:/resource/readList";
    }

    @GetMapping("/purchase")
    public String purchase(@RequestParam Integer id,
                           @RequestParam int amount,
                           @RequestParam String category
                           ) {

        resourceService.updateProductStock(id, amount, category);

        return "redirect:/resource/readList";
    }
}
