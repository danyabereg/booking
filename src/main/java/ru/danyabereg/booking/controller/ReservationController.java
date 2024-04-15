package ru.danyabereg.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danyabereg.booking.model.dto.RequestDto;
import ru.danyabereg.booking.model.dto.ReservationDto;
import ru.danyabereg.booking.service.ReservationService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Object> doReservation(@RequestBody RequestDto requestDto, @RequestHeader("X-User-Name") String userName) {
        try {
            ReservationDto reservationDto = reservationService.createReservation(requestDto, userName);
            return ResponseEntity.status(HttpStatus.OK).body(reservationDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(requestDto);
        }
    }

    @DeleteMapping("/{reservationUid}")
    public ResponseEntity<Object> deleteReservation(@RequestHeader("X-User-Name") String userName, @PathVariable UUID reservationUid) {
        boolean result = reservationService.deleteReservation(userName, reservationUid);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(reservationUid + " deleted");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reservationUid + " NOT FOUND");
        }
    }
}
