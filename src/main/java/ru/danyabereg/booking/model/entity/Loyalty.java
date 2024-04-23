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
@Entity
@Table(name = "loyalty")
public class Loyalty {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "booking_quantity", nullable = false)
    private Integer bookingQuantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status", nullable = false)
    private StatusDiscount status;

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
