package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.HotelMapper;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.mapper.ReservationMapper;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.dto.RequestDto;
import ru.danyabereg.booking.model.dto.ReservationDto;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.Reservation;
import ru.danyabereg.booking.model.entity.ReservationStatus;
import ru.danyabereg.booking.model.repository.ReservationRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final LoyaltyMapper loyaltyMapper;
    private final HotelMapper hotelMapper;
    private final HotelService hotelService;
    private final LoyaltyService loyaltyService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ReservationDto createReservation(RequestDto requestDto, String userName) {
        LOGGER.info("Try to find hotel");
        Optional<HotelDto> optionalHotelDto = hotelService.findById(requestDto.getHotelUid());
        if (optionalHotelDto.isEmpty()) {
            LOGGER.info("Hotel not found");
            return null;
        }
        LOGGER.info("Hotel found");
        HotelDto hotelDto = optionalHotelDto.get();
        LOGGER.info("Try to find user");
        Optional<LoyaltyDto> optionalLoyaltyDto = loyaltyService.findByUserName(userName);
        LoyaltyDto loyaltyDto = optionalLoyaltyDto.orElseGet(() ->
                loyaltyService.createUser(userName));
        LOGGER.info("user found");
        Reservation reservation = Reservation.builder()
                .loyalty(loyaltyMapper.mapToEntity(loyaltyDto))
                .hotel(hotelMapper.mapToEntity(hotelDto))
                .status(ReservationStatus.SUCCESS)
                .dateFrom(requestDto.getStartDate())
                .dateTo(requestDto.getEndDate())
                .build();
        LOGGER.info("Saving reservation");
        reservation = reservationRepository.saveAndFlush(reservation);
        LOGGER.info("Try to find hotel");
        return reservationMapper.mapToDto(reservation);
    }
}
