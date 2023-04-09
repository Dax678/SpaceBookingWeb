package com.example.spacebookingweb.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/web/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/web/logout")
    public String getLogout() {
        return "logout";
    }
}
