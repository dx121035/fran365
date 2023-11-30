package com.example.fran365.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/status")
@Controller
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping("/create")
    public String create(Status status){

        statusService.create(status);

        return "redirect:/delivery/readList";
    }
}
