package ru.danyabereg.booking.mapper;

import org.springframework.stereotype.Component;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.StatusDiscount;

@Component
public class StatusDiscountMapper implements Mapper<StatusDiscountDto, StatusDiscount> {
    @Override
    public StatusDiscount mapToEntity(StatusDiscountDto dto) {
        return StatusDiscount.builder()
                .discountStatus(dto.getDiscountStatus())
                .discount(dto.getDiscount())
                .minQuantity(dto.getMinQuantity())
                .maxQuantity(dto.getMaxQuantity())
                .build();
    }

    @Override
    public StatusDiscountDto mapToDto(StatusDiscount entity) {
        return StatusDiscountDto.builder()
                .discountStatus(entity.getDiscountStatus())
                .discount(entity.getDiscount())
                .minQuantity(entity.getMinQuantity())
                .maxQuantity(entity.getMaxQuantity())
                .build();
    }
}
