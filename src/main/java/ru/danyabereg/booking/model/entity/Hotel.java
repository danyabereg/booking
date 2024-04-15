package ru.danyabereg.booking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@ToString(exclude = "reservations")
//@EqualsAndHashCode(exclude = "reservations")
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "hotel_name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "night_price", nullable = false)
    private BigDecimal price;

//    @Builder.Default
//    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Reservation> reservations = new ArrayList<>();

//    public void addReservation(Reservation reservation) {
//        this.reservations.add(reservation);
//    }
}
