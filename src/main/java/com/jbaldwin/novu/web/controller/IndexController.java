package com.jbaldwin.novu.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api")
public class IndexController {

    @GetMapping
    public String index() {
        return "This is the api!";
    }

}
