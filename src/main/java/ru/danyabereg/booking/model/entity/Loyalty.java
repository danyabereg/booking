package ru.danyabereg.booking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString(exclude = "reservations")
//@EqualsAndHashCode(exclude = "reservations")
@Entity
@Table(name = "loyalty")
public class Loyalty {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "booking_quantity", nullable = false)
    private Integer bookingQuantity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoyaltyStatus status;

//    @Builder.Default
//    @OneToMany(mappedBy = "loyalty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Reservation> reservations = new ArrayList<>();

    public Loyalty incrementQuantity() {
        this.bookingQuantity += 1;
        return this;
    }

    public Loyalty decrementQuantity() {
        if (this.bookingQuantity != 0) {
            this.bookingQuantity -= 1;
        }
        return this;
    }

}
