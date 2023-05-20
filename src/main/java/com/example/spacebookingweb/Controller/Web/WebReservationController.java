package com.example.spacebookingweb.Controller.Web;

import com.example.spacebookingweb.Database.View.UserReservationView;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;
import java.util.Comparator;

@Controller
public class WebReservationController {
    ReservationService reservationService;
    UserService userService;
    @Autowired
    public WebReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/web/reservations")
    public String getWebReservationController(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "nieznany użytkownik";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        model.addAttribute("reservationList",
                reservationService.getReservationByUserId(
                        userService.getUserByUsername(username).getId())
                        .stream().sorted(Comparator.comparing(UserReservationView::getReservation_date))
                        .toList()
        );

        return "reservationPage";
    }
}
