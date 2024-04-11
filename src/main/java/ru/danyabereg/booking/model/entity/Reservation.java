package ru.danyabereg.booking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "username", nullable = false)
    private Loyalty user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "date_from")
    private LocalDate dateFrom;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "date_to")
    private LocalDate dateTo;
}
