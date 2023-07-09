package com.example.spacebookingweb.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DeleteBookingRequest {
    @NotBlank
    private Long user_id;
    @NotBlank
    private Long reservation_id;
}
