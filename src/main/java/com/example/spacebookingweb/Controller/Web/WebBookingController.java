package com.example.spacebookingweb.Controller.Web;

import com.example.spacebookingweb.Controller.FloorController;
import com.example.spacebookingweb.Controller.SpaceController;
import com.example.spacebookingweb.Database.Entity.Floor;
import com.example.spacebookingweb.Service.ReservationService;
import com.example.spacebookingweb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WebBookingController {
    ReservationService reservationService;
    UserService userService;
    FloorController floorService;
    SpaceController spaceService;

    @Autowired
    public WebBookingController(ReservationService reservationService, UserService userService, FloorController floorService, SpaceController spaceService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.floorService = floorService;
        this.spaceService = spaceService;
    }

    @GetMapping(value = "/web/booking")
    public String getWebReservationController(@RequestParam(value = "floorName", required = false, defaultValue = "Wybierz pietro") String floorName, @RequestParam(value = "spaceType", required = false) String spaceType, @RequestParam(value = "date", required = false, defaultValue = "") LocalDate date, Model model) {
        model.addAttribute("floorList", floorService.getFloorList().getBody());
        model.addAttribute("spaceList", spaceService.getAll().getBody());
        model.addAttribute("selectedFloor", floorName.substring(0, floorName.indexOf(" ")));
        model.addAttribute("selectedSpace", floorName.substring(0, floorName.indexOf(" ")));
        model.addAttribute("selectedDate", date);

        Floor floor = floorService.getFloorByName(floorName.substring(0, floorName.indexOf(" "))).getBody();

        if(floor != null) {
            model.addAttribute("results", spaceService.getSpacesByFloorIdAndType(floor.getId(), spaceType, date).getBody());
        }

        return "bookSpacePage";
    }

//    @PostMapping("/web/booking/{id}")
//    public void bookSpace(@PathVariable(value = "id") Long spaceId, @RequestParam("reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = "nieznany użytkownik";
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails)principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        reservationService.saveReservation(userService.getUserByUsername(username).getId(), spaceId, reservationDate);
//    }
}
