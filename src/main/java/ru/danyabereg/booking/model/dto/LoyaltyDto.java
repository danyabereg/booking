package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.danyabereg.booking.model.entity.LoyaltyStatus;

@AllArgsConstructor
@Builder
@Value
public class LoyaltyDto {
    String userName;
    Integer bookingQuantity;
    LoyaltyStatus status;
}
