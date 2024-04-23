package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class StatusDiscountDto {
    String discountStatus;
    Integer discount;
    Integer minQuantity;
    Integer maxQuantity;
}
