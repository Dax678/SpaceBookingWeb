package com.example.spacebookingweb.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebProfileController {
    @GetMapping("/web/profile")
    public String getWebIndexController(Model model) {
        return "profile";
    }
}
