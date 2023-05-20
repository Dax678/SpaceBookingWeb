package com.example.spacebookingweb.Controller.Web;

import com.example.spacebookingweb.Controller.UserDetailsController;
import com.example.spacebookingweb.Database.Entity.UserDetails;
import com.example.spacebookingweb.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebProfileController {
    UserDetailsController userDetailsController;
    UserService userService;

    @Autowired
    public WebProfileController(UserDetailsController userDetailsController, UserService userService) {
        this.userDetailsController = userDetailsController;
        this.userService = userService;
    }

    @GetMapping("/web/profile")
    public String getWebIndexController(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "nieznany użytkownik";
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            username = ((org.springframework.security.core.userdetails.UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Long userId = userService.getUserByUsername(username).getId();

        UserDetails userDetails = userDetailsController.getUserDetailsById(userId).getBody();

        model.addAttribute("name", userDetails != null ? userDetails.getName() : "Brak ustawionego imienia");
        model.addAttribute("surname", userDetails != null ? userDetails.getSurname() : "Brak ustawionego nazwiska");
        model.addAttribute("phoneNumber", userDetails != null ? userDetails.getPhoneNumber() : "Brak ustawionego numeru telefonu");
        model.addAttribute("address", userDetails != null ? userDetails.getAddress() : "Brak ustawionego adresu zamieszkania");
        model.addAttribute("email", userService.getUserById(userId).getEmail());

        return "profilePage";
    }
}
