package com.example.spacebookingweb.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {
    @NotBlank
    private Long userId;
    @NotBlank
    private Long spaceId;
    @NotBlank
    private LocalDate date;
    @NotBlank
    private Boolean reservationStatus;
}
