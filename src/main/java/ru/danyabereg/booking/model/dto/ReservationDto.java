package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.danyabereg.booking.model.entity.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Value
public class ReservationDto {
    UUID id;
    LoyaltyDto loyaltyDto;
    HotelDto hotelDto;
    ReservationStatus status;
    LocalDate dateFrom;
    LocalDate dateTo;
    String userStatus;
    BigDecimal price;
    LocalDate lastUpdate;
}
