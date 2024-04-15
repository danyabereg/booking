package ru.danyabereg.booking.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danyabereg.booking.model.entity.Loyalty;

import java.util.Optional;

@Repository
public interface LoyaltyRepository extends JpaRepository<Loyalty, String> {
    Optional<Loyalty> findByUserName(String userName);
}
