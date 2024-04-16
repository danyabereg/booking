package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Value
public class ReservationResponseDto {
    ReservationDto reservationDto;
    int duration;
    BigDecimal price;
}
