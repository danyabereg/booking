package ru.danyabereg.booking.mapper;

import org.springframework.stereotype.Component;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.entity.Loyalty;

@Component
public class LoyaltyMapper implements Mapper<LoyaltyDto, Loyalty>{
    @Override
    public Loyalty mapToEntity(LoyaltyDto dto) {
        return Loyalty.builder()
                .userName(dto.getUserName())
                .bookingQuantity(dto.getBookingQuantity())
                .status(dto.getStatus())
                .build();
    }

    @Override
    public LoyaltyDto mapToDto(Loyalty entity) {
        return LoyaltyDto.builder()
                .userName(entity.getUserName())
                .bookingQuantity(entity.getBookingQuantity())
                .status(entity.getStatus())
                .build();
    }
}
