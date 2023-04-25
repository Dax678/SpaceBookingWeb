package com.example.spacebookingweb.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebRegisterController {
    @GetMapping("/web/register")
    public String getRegister() {
        return "registerPage";
    }

    @GetMapping("/web/TermsOfServicePage")
    public String getTermsOfServicePage() {
        return "TermsOfServicePage";
    }
}
