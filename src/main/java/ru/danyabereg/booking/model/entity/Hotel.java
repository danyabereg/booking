package ru.danyabereg.booking.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "hotel_name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "night_price", nullable = false)
    private BigDecimal price;
}
