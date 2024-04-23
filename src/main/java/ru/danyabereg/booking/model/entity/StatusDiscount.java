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
@Table(name = "status_discount")
public class StatusDiscount {

    @Id
    @Column(name = "status", nullable = false, unique = true)
    private String discountStatus;

    @Column(name = "discount_percent", nullable = false)
    private Integer discount;

    @Column(name = "min_quantity", nullable = false, unique = true)
    private Integer minQuantity;

    @Column(name = "max_quantity", unique = true)
    private Integer maxQuantity;
}
