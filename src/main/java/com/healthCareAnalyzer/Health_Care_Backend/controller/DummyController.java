package com.healthCareAnalyzer.Health_Care_Backend.controller;

import com.healthCareAnalyzer.Health_Care_Backend.service.DummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class DummyController {


    @Autowired
    private DummyService dummyService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAllProducts() {
        return dummyService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getProductById(@PathVariable int id) {
        return "product"+id;
    }

    @GetMapping("/welcome")
    public String getWelcome() {
        return "Not Secure";
    }


}
