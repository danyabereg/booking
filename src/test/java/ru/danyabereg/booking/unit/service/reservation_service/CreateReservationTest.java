package ru.danyabereg.booking.unit.service.reservation_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.HotelMapper;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.mapper.ReservationMapper;
import ru.danyabereg.booking.model.dto.*;
import ru.danyabereg.booking.model.entity.Hotel;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.Reservation;
import ru.danyabereg.booking.model.entity.StatusDiscount;
import ru.danyabereg.booking.model.repository.ReservationRepository;
import ru.danyabereg.booking.service.HotelService;
import ru.danyabereg.booking.service.LoyaltyService;
import ru.danyabereg.booking.service.ReservationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertNull;
import static ru.danyabereg.booking.model.entity.ReservationStatus.SUCCESS;

@SpringBootTest
public class CreateReservationTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private HotelService hotelService;
    @Mock
    private LoyaltyService loyaltyService;
    @Spy
    private HotelMapper hotelMapper;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private LoyaltyMapper loyaltyMapper;
    @InjectMocks
    private ReservationService reservationService;

    private static final UUID HOTEL_ID = UUID.randomUUID();
    private static final UUID RESERVATION_ID = UUID.randomUUID();
    private static final ReservationRequestDto REQUEST_DTO = new ReservationRequestDto(
            HOTEL_ID, LocalDate.now().minusDays(3), LocalDate.now());
    private static final HotelDto HOTEL_DTO = new HotelDto(
            HOTEL_ID, "test", "test", BigDecimal.TEN);
    private static final Hotel HOTEL = new Hotel(
            HOTEL_ID, "test", "test", BigDecimal.TEN);
    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            "BRONZE", 5, 0, 9);
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            "BRONZE", 5, 0, 9);
    private static final LoyaltyDto LOYALTY_DTO = new LoyaltyDto(
            "test", 1, STATUS_DISCOUNT_DTO);
    private static final Loyalty LOYALTY = new Loyalty(
            "test", 1, STATUS_DISCOUNT);
    private static final Reservation RESERVATION = new Reservation(
            RESERVATION_ID, LOYALTY, HOTEL, SUCCESS, REQUEST_DTO.getStartDate(),
            REQUEST_DTO.getEndDate(), LOYALTY.getStatus().getDiscountStatus(),
            HOTEL.getPrice()
                    .multiply(BigDecimal.valueOf(1.0 - 0.01 * LOYALTY.getStatus().getDiscount()))
                    .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(REQUEST_DTO.getStartDate(), REQUEST_DTO.getEndDate()))),
            LocalDate.now());
    private static final ReservationDto RESERVATION_DTO = new ReservationDto(
            RESERVATION_ID, LOYALTY_DTO, HOTEL_DTO, SUCCESS, REQUEST_DTO.getStartDate(),
            REQUEST_DTO.getEndDate(), LOYALTY.getStatus().getDiscountStatus(),
            HOTEL.getPrice()
                    .multiply(BigDecimal.valueOf(1.0 - 0.01 * LOYALTY.getStatus().getDiscount()))
                    .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(REQUEST_DTO.getStartDate(), REQUEST_DTO.getEndDate()))),
            LocalDate.now());

    @Test
    void createReservationDefaultTest() {
        Reservation reservation = Reservation.builder()
                .loyalty(LOYALTY)
                .hotel(HOTEL)
                .status(SUCCESS)
                .dateFrom(REQUEST_DTO.getStartDate())
                .dateTo(REQUEST_DTO.getEndDate())
                .userStatus(LOYALTY.getStatus().getDiscountStatus())
                .price(HOTEL.getPrice()
                        .multiply(BigDecimal.valueOf(1.0 - 0.01 * LOYALTY.getStatus().getDiscount()))
                        .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(REQUEST_DTO.getStartDate(), REQUEST_DTO.getEndDate()))))
                .lastUpdate(LocalDate.now())
                .build();

        Mockito.doReturn(Optional.of(HOTEL_DTO))
                .when(hotelService).findById(HOTEL_ID);

        Mockito.doReturn(LOYALTY_DTO)
                .when(loyaltyService).findByUserCreate("test");

        Mockito.doReturn(RESERVATION)
                .when(reservationRepository).saveAndFlush(reservation);

        Mockito.doReturn(LOYALTY)
                .when(loyaltyMapper).mapToEntity(LOYALTY_DTO);

        Mockito.doReturn(RESERVATION_DTO)
                .when(reservationMapper).mapToDto(RESERVATION);

        ReservationDto actualResult = reservationService.createReservation(REQUEST_DTO, "test");

        assertEquals(RESERVATION_DTO, actualResult);
        verify(hotelService).findById(HOTEL_ID);
        verify(loyaltyService).findByUserCreate("test");
        verify(loyaltyMapper).mapToEntity(LOYALTY_DTO);
        verify(hotelMapper).mapToEntity(HOTEL_DTO);
        verify(reservationRepository).saveAndFlush(reservation);
    }

    @Test
    void createReservationHotelNotFoundTest() {
        Mockito.doReturn(Optional.empty())
                .when(hotelService).findById(HOTEL_ID);

        ReservationDto actualResult = reservationService.createReservation(REQUEST_DTO, "test");

        assertNull(null, actualResult);
        verify(hotelService).findById(HOTEL_ID);
    }

    @Test
    void createReservationUserDoesntExistTest() {
        Reservation reservation = Reservation.builder()
                .loyalty(LOYALTY)
                .hotel(HOTEL)
                .status(SUCCESS)
                .dateFrom(REQUEST_DTO.getStartDate())
                .dateTo(REQUEST_DTO.getEndDate())
                .userStatus(LOYALTY.getStatus().getDiscountStatus())
                .price(HOTEL.getPrice()
                        .multiply(BigDecimal.valueOf(1.0 - 0.01 * LOYALTY.getStatus().getDiscount()))
                        .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(REQUEST_DTO.getStartDate(), REQUEST_DTO.getEndDate()))))
                .lastUpdate(LocalDate.now())
                .build();

        Mockito.doReturn(Optional.of(HOTEL_DTO))
                .when(hotelService).findById(HOTEL_ID);

        Mockito.doThrow(NoSuchElementException.class)
                .when(loyaltyService).findByUserCreate("test");

        Mockito.doReturn(LOYALTY_DTO)
                .when(loyaltyService).createUser("test");

        Mockito.doReturn(RESERVATION)
                .when(reservationRepository).saveAndFlush(reservation);

        Mockito.doReturn(LOYALTY)
                .when(loyaltyMapper).mapToEntity(LOYALTY_DTO);

        Mockito.doReturn(RESERVATION_DTO)
                .when(reservationMapper).mapToDto(RESERVATION);

        ReservationDto actualResult = reservationService.createReservation(REQUEST_DTO, "test");

        assertEquals(RESERVATION_DTO, actualResult);
        verify(hotelService).findById(HOTEL_ID);
        verify(loyaltyService).findByUserCreate("test");
        verify(loyaltyService).createUser("test");
        verify(loyaltyMapper).mapToEntity(LOYALTY_DTO);
        verify(hotelMapper).mapToEntity(HOTEL_DTO);
        verify(reservationRepository).saveAndFlush(reservation);
    }
}
