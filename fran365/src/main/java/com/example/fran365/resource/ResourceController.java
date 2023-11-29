package com.example.fran365.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/resource")
@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/create")
    public String create() {


        return "resource/create";
    }

    @PostMapping("/create")
    public String create(Resource resource) {

        resourceService.create(resource);

        return "redirect:/resource/readList";
    }

    @GetMapping("/readList")
    public String readList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<Resource> paging = resourceService.getList(page);
        model.addAttribute("paging", paging);


        return "resource/readList";
    }

    @GetMapping("/readDetail")
    public String readDetail(@RequestParam Integer id, Model model) {

       model.addAttribute("resource", resourceService.readDetail(id));

       return "resource/readDetail";
    }

    @GetMapping("/update")
    public String update(@RequestParam Integer id, Model model) {

        Resource resource = resourceService.readDetail(id);

        model.addAttribute("resource", resource);

        return "readDetail";
    }

    @PostMapping("/update")
    public String update(Resource resource) {

        Integer id = resource.getId();

        resourceService.update(resource);

        return "redirect:/resource/readDetail?id=" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {

        resourceService.delete(id);

        return "redirect:/resource/readList";
    }

    @GetMapping("/purchase")
    public String purchase(@RequestParam Integer id, @RequestParam int amount){

        resourceService.updateProductStock(id, amount);

        return "redirect:/resource/readList";
    }
}
