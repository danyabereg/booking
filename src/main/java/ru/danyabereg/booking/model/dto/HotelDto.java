package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Value
public class HotelDto {
    UUID id;
    String name;
    String address;
    BigDecimal price;
}
