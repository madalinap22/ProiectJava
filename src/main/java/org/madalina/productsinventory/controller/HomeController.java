package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.service.ProductsService2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final ProductsService2 prodServ;
    public HomeController(ProductsService2 prodServ){
        this.prodServ = prodServ;
    }

    @GetMapping
    public String home(){
        prodServ.saySomething();
        return "home";
    }

}
