package com.example.spacebookingweb.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {
    @NotBlank
    private Long user_id;
    @NotBlank
    private Long space_id;
    @NotBlank
    private LocalDate date;
    @NotBlank
    private Boolean reservation_status;
}
