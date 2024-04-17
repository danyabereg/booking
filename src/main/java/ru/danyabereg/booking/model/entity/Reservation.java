package ru.danyabereg.booking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private Loyalty loyalty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private DiscountStatus userStatus;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "last_update", nullable = false)
    private LocalDate lastUpdate;

}
