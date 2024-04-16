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
import ru.danyabereg.booking.model.dto.ReservationRequestDto;
import ru.danyabereg.booking.model.dto.ReservationResponseDto;
import ru.danyabereg.booking.model.entity.Reservation;
import ru.danyabereg.booking.model.entity.ReservationStatus;
import ru.danyabereg.booking.model.repository.ReservationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Retryable(
        retryFor = {SQLException.class},
        maxAttemptsExpression = "${db.connection.retry.max_attempts}",
        backoff = @Backoff(delayExpression = "{db.connection.retry.backoff_ms}")
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

    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto, String userName) {
        Optional<HotelDto> optionalHotelDto = hotelService.findById(reservationRequestDto.getHotelUid());
        if (optionalHotelDto.isEmpty()) {
            LOGGER.info("Hotel not found");
            return null;
        }
        HotelDto hotelDto = optionalHotelDto.get();
        Optional<LoyaltyDto> optionalLoyaltyDto = loyaltyService.findByUserCreate(userName);
        LoyaltyDto loyaltyDto = optionalLoyaltyDto.orElseGet(() -> loyaltyService.createUser(userName));
        Reservation reservation = buildReservation(reservationRequestDto, loyaltyDto, hotelDto);
        reservation = reservationRepository.saveAndFlush(reservation);
        return buildReservationResponse(reservation, hotelDto, loyaltyDto);
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

    private Reservation buildReservation(ReservationRequestDto reservationRequestDto, LoyaltyDto loyaltyDto, HotelDto hotelDto) {
        return Reservation.builder()
                .loyalty(loyaltyMapper.mapToEntity(loyaltyDto))
                .hotel(hotelMapper.mapToEntity(hotelDto))
                .status(ReservationStatus.SUCCESS)
                .dateFrom(reservationRequestDto.getStartDate())
                .dateTo(reservationRequestDto.getEndDate())
                .build();
    }

    private ReservationResponseDto buildReservationResponse(Reservation reservation, HotelDto hotelDto, LoyaltyDto loyaltyDto) {
        int duration = reservation.getDateFrom().until(reservation.getDateTo()).getDays();
        return ReservationResponseDto.builder()
                .reservationDto(reservationMapper.mapToDto(reservation))
                .duration(duration)
                .price(getPrice(hotelDto, duration, loyaltyDto))
                .build();
    }

    private BigDecimal getPrice(HotelDto hotelDto, int duration, LoyaltyDto loyaltyDto) {
        return switch (loyaltyDto.getStatus()) {
            case GOLD -> hotelDto.getPrice()
                        .multiply(BigDecimal.valueOf(duration))
                        .multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.UP);
            case SILVER -> hotelDto.getPrice()
                        .multiply(BigDecimal.valueOf(duration))
                        .multiply(BigDecimal.valueOf(0.93)).setScale(2, RoundingMode.UP);
            case BRONZE -> hotelDto.getPrice()
                    .multiply(BigDecimal.valueOf(duration))
                    .multiply(BigDecimal.valueOf(0.95)).setScale(2, RoundingMode.UP);
        };
    }
}
