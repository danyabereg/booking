package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@AllArgsConstructor
@Builder
public class RequestDto {
    UUID hotelUid;
    LocalDate startDate;
    LocalDate endDate;
}
