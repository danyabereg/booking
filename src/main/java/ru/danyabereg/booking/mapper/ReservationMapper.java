package ru.danyabereg.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.danyabereg.booking.model.dto.ReservationDto;
import ru.danyabereg.booking.model.entity.Reservation;

@RequiredArgsConstructor
@Component
public class ReservationMapper implements Mapper<ReservationDto, Reservation> {

    private final HotelMapper hotelMapper;
    private final LoyaltyMapper loyaltyMapper;

    @Override
    public Reservation mapToEntity(ReservationDto dto) {
        return Reservation.builder()
                .id(dto.getId())
                .loyalty(loyaltyMapper.mapToEntity(dto.getLoyaltyDto()))
                .hotel(hotelMapper.mapToEntity(dto.getHotelDto()))
                .status(dto.getStatus())
                .dateFrom(dto.getDateFrom())
                .dateTo(dto.getDateTo())
                .userStatus(dto.getUserStatus())
                .price(dto.getPrice())
                .lastUpdate(dto.getLastUpdate())
                .build();
    }

    @Override
    public ReservationDto mapToDto(Reservation entity) {
        return ReservationDto.builder()
                .id(entity.getId())
                .loyaltyDto(loyaltyMapper.mapToDto(entity.getLoyalty()))
                .hotelDto(hotelMapper.mapToDto(entity.getHotel()))
                .status(entity.getStatus())
                .dateFrom(entity.getDateFrom())
                .dateTo(entity.getDateTo())
                .userStatus(entity.getUserStatus())
                .price(entity.getPrice())
                .lastUpdate(entity.getLastUpdate())
                .build();
    }
}
