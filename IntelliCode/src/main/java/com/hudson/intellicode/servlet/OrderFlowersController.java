package com.hudson.intellicode.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderFlowersController {
	@RequestMapping("/lex/OrderFlowers")
    @ResponseBody
    String orderFlowers() {
        return "Hello World!";
    }
}
