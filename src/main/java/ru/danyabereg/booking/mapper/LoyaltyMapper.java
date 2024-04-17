package ru.danyabereg.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.entity.Loyalty;

@Component
@RequiredArgsConstructor
public class LoyaltyMapper implements Mapper<LoyaltyDto, Loyalty> {
    private final StatusDiscountMapper discountMapper;

    @Override
    public Loyalty mapToEntity(LoyaltyDto dto) {
        return Loyalty.builder()
                .userName(dto.getUserName())
                .bookingQuantity(dto.getBookingQuantity())
                .status(discountMapper.mapToEntity(dto.getStatus()))
                .build();
    }

    @Override
    public LoyaltyDto mapToDto(Loyalty entity) {
        return LoyaltyDto.builder()
                .userName(entity.getUserName())
                .bookingQuantity(entity.getBookingQuantity())
                .status(discountMapper.mapToDto(entity.getStatus()))
                .build();
    }
}
