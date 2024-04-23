package ru.danyabereg.booking.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.danyabereg.booking.model.entity.StatusDiscount;

import java.util.Optional;

@Repository
public interface StatusDiscountRepository extends JpaRepository<StatusDiscount, Integer> {
    Optional<StatusDiscount> findByDiscountStatus(String status);

    @Query(value = """
            SELECT * FROM status_discount
            WHERE discount_percent = (
            SELECT min(discount_percent)
            FROM status_discount)
            """, nativeQuery = true)
    Optional<StatusDiscount> findMinDiscountStatus();

    @Query(value = """
            SELECT * FROM status_discount
            WHERE discount_percent > ?1
            ORDER BY discount_percent
            LIMIT 1
                        """, nativeQuery = true)
    Optional<StatusDiscount> findNextStatus(@Param("1") Integer discount);

    @Query(value = """
            SELECT * FROM status_discount
            WHERE discount_percent < ?1
            ORDER BY discount_percent DESC
            LIMIT 1
                        """, nativeQuery = true)
    Optional<StatusDiscount> findPreviousStatus(@Param("1") Integer discount);
}
