package com.example.spacebookingweb.Controller.Web;

import com.example.spacebookingweb.Controller.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class WebRegisterController {
    UserController userController;
    @GetMapping("/web/register")
    public String getRegister() {
        return "registerPage";
    }

    @GetMapping("/web/TermsOfServicePage")
    public String getTermsOfServicePage() {
        return "TermsOfServicePage";
    }

    //TODO: POPRAWIĆ
    @PostMapping(value = "/web/addUser")
    public String saveUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "email") String email) {
        userController.addUser(username, password, email);
        return "redirect:/web/login";
    }
}
