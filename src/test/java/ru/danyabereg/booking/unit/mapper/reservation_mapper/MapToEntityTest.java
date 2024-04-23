package ru.danyabereg.booking.unit.mapper.reservation_mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.ReservationMapper;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.dto.ReservationDto;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapToEntityTest {
    private static final UUID RESERVATION_ID = UUID.randomUUID();
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            "SILVER", 7, 10, 19);
    private static final Loyalty LOYALTY = new Loyalty(
            "test", 10, STATUS_DISCOUNT);
    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            "SILVER", 7, 10, 19);
    private static final LoyaltyDto LOYALTY_DTO = new LoyaltyDto(
            "test", 10, STATUS_DISCOUNT_DTO);
    private static final UUID HOTEL_ID = UUID.randomUUID();
    private static final Hotel HOTEL = new Hotel(
            HOTEL_ID, "test", "test", BigDecimal.ONE);
    private static final HotelDto HOTEL_DTO = new HotelDto(
            HOTEL_ID, "test", "test", BigDecimal.ONE);
    private static final Reservation RESERVATION = new Reservation(
            RESERVATION_ID, LOYALTY, HOTEL, ReservationStatus.SUCCESS,
            LocalDate.now().minusDays(10),
            LocalDate.now(), STATUS_DISCOUNT.getDiscountStatus(), BigDecimal.ONE, LocalDate.now());
    private static final ReservationDto RESERVATION_DTO = new ReservationDto(
            RESERVATION_ID, LOYALTY_DTO, HOTEL_DTO, ReservationStatus.SUCCESS,
            LocalDate.now().minusDays(10),
            LocalDate.now(), STATUS_DISCOUNT_DTO.getDiscountStatus(), BigDecimal.ONE, LocalDate.now());

    @Autowired
    private ReservationMapper reservationMapper;

    @Test
    void mapToEntityTest() {
        Reservation actualResult = reservationMapper.mapToEntity(RESERVATION_DTO);

        assertEquals(actualResult, RESERVATION);
    }
}
