package ru.danyabereg.booking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String userName;

    @Column(name = "booking_quantity", nullable = false)
    private Integer bookingQuantity;

    @Column(name = "status", nullable = false)
    private LoyaltyStatus status;

}
