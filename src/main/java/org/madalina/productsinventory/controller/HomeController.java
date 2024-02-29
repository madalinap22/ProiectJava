package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final HomeService prodServ;
    public HomeController(HomeService prodServ){
        this.prodServ = prodServ;
    }

    @GetMapping
    public ResponseEntity<String> home(){
        String message = prodServ.saySomething();
        return ResponseEntity.ok(message);
    }

}
