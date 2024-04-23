package ru.danyabereg.booking.unit.service.reservation_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.model.entity.Reservation;
import ru.danyabereg.booking.model.entity.ReservationStatus;
import ru.danyabereg.booking.model.repository.ReservationRepository;
import ru.danyabereg.booking.service.LoyaltyService;
import ru.danyabereg.booking.service.ReservationService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DeleteReservationTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private LoyaltyService loyaltyService;
    @InjectMocks
    private ReservationService reservationService;

    private static final UUID ID = UUID.randomUUID();

    @Test
    void deleteSuccessReservationTest() {
        var reservation = Reservation.builder()
                .id(ID)
                .status(ReservationStatus.SUCCESS)
                .lastUpdate(LocalDate.now().minusDays(10))
                .build();
        Mockito.doReturn(Optional.of(reservation))
                .when(reservationRepository).findById(ID);
        Mockito.doNothing().when(loyaltyService)
                .findByUserDelete("test");

        assertTrue(reservationService.deleteReservation("test", ID));
        assertEquals(reservation.getStatus(), ReservationStatus.CANCELED);
        assertEquals(reservation.getLastUpdate(), LocalDate.now());
        verify(loyaltyService).findByUserDelete("test");
    }

    @Test
    void deleteCanceledReservationTest() {
        var reservation = Reservation.builder()
                .id(ID)
                .status(ReservationStatus.CANCELED)
                .lastUpdate(LocalDate.now().minusDays(10))
                .build();
        Mockito.doReturn(Optional.of(reservation))
                .when(reservationRepository).findById(ID);

        assertFalse(reservationService.deleteReservation("test", ID));
        assertEquals(reservation.getStatus(), ReservationStatus.CANCELED);
        assertEquals(reservation.getLastUpdate(), LocalDate.now().minusDays(10));
    }

    @Test
    void deleteEmptyReservationTest() {
        Mockito.doReturn(Optional.empty())
                .when(reservationRepository).findById(ID);

        assertFalse(reservationService.deleteReservation("test", ID));
    }
}
