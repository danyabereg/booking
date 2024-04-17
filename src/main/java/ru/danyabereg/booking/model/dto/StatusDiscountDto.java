package ru.danyabereg.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.danyabereg.booking.model.entity.DiscountStatus;

@AllArgsConstructor
@Builder
@Value
public class StatusDiscountDto {
    DiscountStatus discountStatus;
    Integer discount;
}
