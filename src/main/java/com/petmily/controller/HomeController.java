package com.petmily.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/api/home")
    public String home() {

        return "home";
    }

    // cloudtype 콜드 스타트 방지용 api
    @GetMapping("/api/health")
    public String healthCheck() {

        return "OK";
    }
}
