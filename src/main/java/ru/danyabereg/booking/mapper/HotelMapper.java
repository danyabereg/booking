package ru.danyabereg.booking.mapper;

import org.springframework.stereotype.Component;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.entity.Hotel;

@Component
public class HotelMapper implements Mapper<HotelDto, Hotel> {
    @Override
    public Hotel mapToEntity(HotelDto dto) {
        return Hotel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public HotelDto mapToDto(Hotel entity) {
        return HotelDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .price(entity.getPrice())
                .build();
    }
}
