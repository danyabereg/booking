package ru.danyabereg.booking.mapper;

import org.springframework.stereotype.Component;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.StatusDiscount;

@Component
public class StatusDiscountMapper implements Mapper<StatusDiscountDto, StatusDiscount> {
    @Override
    public StatusDiscount mapToEntity(StatusDiscountDto dto) {
        return StatusDiscount.builder()
                .status(dto.getDiscountStatus())
                .discount(dto.getDiscount())
                .build();
    }

    @Override
    public StatusDiscountDto mapToDto(StatusDiscount entity) {
        return StatusDiscountDto.builder()
                .discountStatus(entity.getStatus())
                .discount(entity.getDiscount())
                .build();
    }
}
