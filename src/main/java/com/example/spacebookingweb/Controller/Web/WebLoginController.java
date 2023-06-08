package com.example.spacebookingweb.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebLoginController {
    @RequestMapping("/login")
    public String getLogin() {
        return "index";
    }

    @RequestMapping("/logout")
    public String getLogout() {
        return "index";
    }
}
