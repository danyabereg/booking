package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.HotelMapper;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.mapper.ReservationMapper;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.dto.RequestDto;
import ru.danyabereg.booking.model.dto.ReservationDto;
import ru.danyabereg.booking.model.entity.Reservation;
import ru.danyabereg.booking.model.entity.ReservationStatus;
import ru.danyabereg.booking.model.repository.ReservationRepository;

import java.net.ConnectException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Retryable(
        retryFor = { ConnectException.class },
        maxAttempts = 4,
        backoff = @Backoff(delay = 800)
)
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final LoyaltyMapper loyaltyMapper;
    private final HotelMapper hotelMapper;
    private final HotelService hotelService;
    private final LoyaltyService loyaltyService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ReservationDto createReservation(RequestDto requestDto, String userName) {
        Optional<HotelDto> optionalHotelDto = hotelService.findById(requestDto.getHotelUid());
        if (optionalHotelDto.isEmpty()) {
            LOGGER.info("Hotel not found");
            return null;
        }
        HotelDto hotelDto = optionalHotelDto.get();
        Optional<LoyaltyDto> optionalLoyaltyDto = loyaltyService.findByUserCreate(userName);
        LoyaltyDto loyaltyDto = optionalLoyaltyDto.orElseGet(() -> loyaltyService.createUser(userName));
        Reservation reservation = Reservation.builder()
                .loyalty(loyaltyMapper.mapToEntity(loyaltyDto))
                .hotel(hotelMapper.mapToEntity(hotelDto))
                .status(ReservationStatus.SUCCESS)
                .dateFrom(requestDto.getStartDate())
                .dateTo(requestDto.getEndDate())
                .build();
        reservation = reservationRepository.saveAndFlush(reservation);
        return reservationMapper.mapToDto(reservation);
    }

    public boolean deleteReservation(String userName, UUID id) {
        try {
            Optional<Reservation> reservation = reservationRepository.findById(id);
            if (reservation.isPresent() && reservation.get().getStatus() == ReservationStatus.SUCCESS) {
                reservation.get().setStatus(ReservationStatus.CANCELED);
                loyaltyService.findByUserDelete(userName);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
