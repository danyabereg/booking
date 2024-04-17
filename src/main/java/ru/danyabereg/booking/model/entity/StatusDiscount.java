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
    @Enumerated(EnumType.STRING)
    private DiscountStatus status;

    @Column(name = "discount_percent", nullable = false)
    private Integer discount;
}
