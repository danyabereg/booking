package ru.danyabereg.booking.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danyabereg.booking.model.entity.DiscountStatus;
import ru.danyabereg.booking.model.entity.StatusDiscount;

import java.util.Optional;

@Repository
public interface StatusDiscountRepository extends JpaRepository<StatusDiscount, Integer> {
    Optional<StatusDiscount> findByStatus(DiscountStatus status);
}
