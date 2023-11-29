/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.brand;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/brand")
@Controller
public class BrandController {

    @Autowired private BrandService brandService;
   // @Autowired private UserService userService;

    @GetMapping("/create")
    public String create() {
        return "brand/create";
    }

    @PostMapping("/create")
    public String create(Brand brand) {
        brandService.create(brand);
        return "redirect:/brand/list";
    }
    @GetMapping("/list")
    public String list(Model model) {
       model.addAttribute("brand",brandService.list());
       return "brand/list";
   }
  /*    @GetMapping("/list")
      public String list(Model model,@RequestParam(value="page",defaultValue="0")int page) {
          Page<Brand> paging = brandService.getList(page);
          //model.addAttribute("brands",boardService.list());

     model.addAttribute("paging",paging);
     return "brand/list";
      }*/

    @GetMapping("/detail")
    public String detail(Model model,@RequestParam Integer id) {

        //Brand brand = brandService.detail(id);
        //User user = userService.readdetailusername();
       // boardService.hit(brand,user);

        model.addAttribute("brand", brandService.detail(id));

        return "brand/detail";
    }

    @GetMapping("/update")
    public String update(Model model,@RequestParam Integer id) {
        //Brand brand = brandService.detail(id);
        //User user = userService.readdetailusername();
       // boardService.hit(brand,user);

        model.addAttribute("brand", brandService.detail(id));
        return "brand/update";
    }
    @PostMapping("/update")
    public String update(Brand brand) {

        brandService.update(brand);
        return "redirect:/brand/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        brandService.delete(id);
        return "redirect:/brand/list";
    }
}
