package com.ls.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AspenController {

    @RequestMapping("/name")
    public String aspenName(HttpServletRequest request, String a) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        System.out.println(requestAttributes);
        return "baiyang";
    }
}
