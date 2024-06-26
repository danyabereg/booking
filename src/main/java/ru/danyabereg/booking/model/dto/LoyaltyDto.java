package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class LoyaltyDto {
    String userName;
    Integer bookingQuantity;
    StatusDiscountDto status;
}
